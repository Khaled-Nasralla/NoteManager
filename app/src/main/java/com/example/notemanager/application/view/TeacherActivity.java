package com.example.notemanager.application.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.example.notemanager.R;
import com.example.notemanager.application.listener.Listener;
import com.example.notemanager.application.viewmodel.ClassesViewModel;
import com.example.notemanager.application.viewmodel.CourseViewModel;
import com.example.notemanager.application.viewmodel.StudentsViewModel;

import java.util.UUID;

public class TeacherActivity extends AppCompatActivity implements Listener.OnTitleChangeListener {

    private ClassesViewModel classeViewModel;
    private StudentsViewModel studentsViewModel;
    private CourseViewModel courseViewModel;
    private TextView titleTextView;
    private LinearLayout linearLayout;
    private LinearLayout iconLayout;
    private String caller;
    private UUID courseId;
    ImageButton imageButton;
    ImageButton addStudentButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_main);

        // Initialize ViewModel
        classeViewModel = new ViewModelProvider(this).get(ClassesViewModel.class);
        studentsViewModel = new ViewModelProvider(this).get(StudentsViewModel.class);
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        titleTextView = findViewById(R.id.textView);
        linearLayout = findViewById(R.id.linearLayout3);
        iconLayout = findViewById(R.id.icons);

        // Add the fragment if this is the first creation
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, new ClassFragment(this));
            fragmentTransaction.commit();
        }

        // Button to open Add Course dialog
        Button addClassButton = findViewById(R.id.button_add);
        addClassButton.setOnClickListener(v -> onClickAddCourse());


        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment instanceof ClassFragment) {
                caller = "ClassFragment";
                linearLayout.removeView(imageButton);
                iconLayout.removeView(addStudentButton);
            } else if (currentFragment instanceof StudentsFragment) {
                caller = "StudentsFragment";
            }else if (currentFragment instanceof CoursFragment) {
                caller = "CoursFragment";
            }
        });
    }

    @Override
    public void onFragmentsChange(String title, String caller, UUID courseId) {
        titleTextView.setText(title);
        this.caller = caller;
        this.courseId = courseId;
    }

    @Override
    public void onAddBackButton(ImageButton imageButton) {
        this.imageButton = imageButton;
        linearLayout.addView(imageButton);
        if("ClassFragment".equals(caller)){
            linearLayout.removeView(imageButton);
        }
    }

    @Override
    public void onAddStudentButton(ImageButton imageButton) {
        addStudentButton = imageButton;
        iconLayout.addView(addStudentButton);
        if("ClassFragment".equals(caller)){
            iconLayout.removeView(addStudentButton);
        }
    }

    public void onClickAddCourse() {
        if ("ClassFragment".equals(caller)) {
            AddClassDialogFragment addClassDialog = AddClassDialogFragment.newInstance();
            addClassDialog.setOnClassAddedListener(classeViewModel);
            addClassDialog.show(getSupportFragmentManager(), "AddClassDialogFragment");
        } else if ("StudentsFragment".equals(caller)) {
            StudentListDialog studentListDialog = StudentListDialog.newInstance(courseId);
            studentListDialog.setOnStudentAddedListener(studentsViewModel);
            studentListDialog.show(getSupportFragmentManager(), "StudentListDialog");
        } else if ("CoursFragment".equals(caller)) {
            AddCourseDialogFragment addCourseDialog = AddCourseDialogFragment.newInstance(courseId);
            addCourseDialog.setOnCourseAddedListener(courseViewModel);
            addCourseDialog.show(getSupportFragmentManager(), "AddCourseDialogFragment");
        } else if("EvaluationFragment".equals(caller)){
            AddSubEvaluationDialog addEvaluationDialog = AddSubEvaluationDialog.newInstance(courseId);
            addEvaluationDialog.setOnEvaluationAddedListener(courseViewModel);
            addEvaluationDialog.show(getSupportFragmentManager(), "AddEvaluationDialogFragment");
        }
    }

}