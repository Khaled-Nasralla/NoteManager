package com.example.notemanager.application.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notemanager.R;
import com.example.notemanager.application.listener.Listener;
import com.example.notemanager.application.model.Classes;
import com.example.notemanager.application.viewmodel.ClassesViewModel;

import java.util.UUID;

public class ClassFragment extends Fragment {

    private ClassesViewModel classesViewModel;
    private LinearLayout mCourseListLayout;
    private final Listener.OnTitleChangeListener onTitleChangeListener;
    private String caller = "ClassFragment";
    public UUID courseId = null;
    public ClassFragment(Listener.OnTitleChangeListener onTitleChangeListener) {
        this.onTitleChangeListener = onTitleChangeListener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_class, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        classesViewModel = new ViewModelProvider(requireActivity()).get(ClassesViewModel.class);
        mCourseListLayout = view.findViewById(R.id.classes_list);
        updateUI();
        onTitleChangeListener.onFragmentsChange(getString(R.string.title_fragment1),caller, courseId);
    }

    private void updateUI() {
        classesViewModel.getCourses().observe(getViewLifecycleOwner(), courses -> {
            mCourseListLayout.removeAllViews();
            for (Classes course : courses) {
                mCourseListLayout.addView(getCourseView(course));

            }
        });
    }

    private View getCourseView(final Classes course) {
        LinearLayout columnForCourse = getLinearLayout();
        TextView courseName = getTextView();
        Button studentsButton = getButton("Etudiants");

        studentsButton.setOnClickListener(v -> onStudentButtonClicked(course));
        Button EvaluationButton = getButton("Evaluation");
        EvaluationButton.setOnClickListener(v -> onEvaluationButtonClicked(course));
        courseName.setText(course.getNom());
        columnForCourse.addView(courseName);
        columnForCourse.addView(EvaluationButton);
        columnForCourse.addView(studentsButton);
        return columnForCourse;
    }

    private @NonNull Button getButton(String text) {
        Button button = new Button(requireContext());
        button.setPadding(8, 8, 8, 8);
        button.setText(text);

        return button;
    }


    private @NonNull LinearLayout getLinearLayout() {
        LinearLayout columnForCourse = new LinearLayout(requireContext());
        columnForCourse.setOrientation(LinearLayout.VERTICAL);
        columnForCourse.setPadding(8, 8, 8, 8);
        columnForCourse.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 0, 10, 10);
        columnForCourse.setLayoutParams(params);
        columnForCourse.setBackgroundResource(R.drawable.border);
        columnForCourse.setBackgroundColor(getResources().getColor(R.color.greenblue));

        return columnForCourse;
    }

    private @NonNull TextView getTextView() {
        TextView textView = new TextView(requireContext());
        textView.setTextSize(20);
        textView.setPadding(8, 8, 8, 8);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }


    private void onStudentButtonClicked(Classes course) {
        this.courseId = course.getId();
       StudentsFragment blankFragment = new StudentsFragment(courseId, onTitleChangeListener);
         requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, blankFragment)
                .addToBackStack(null)
                .commit();
        caller = "StudentsFragment";
        onTitleChangeListener.onFragmentsChange(getString(R.string.title_fragment2),caller,courseId);
        onTitleChangeListener.onAddBackButton(getImageButton());
    }


    private void onEvaluationButtonClicked(Classes course) {
        this.courseId = course.getId();
       CoursFragment blankFragment = new CoursFragment(courseId, onTitleChangeListener);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, blankFragment)
                .addToBackStack(null)
                .commit();
        caller = "CoursFragment";
        onTitleChangeListener.onFragmentsChange(getString(R.string.title_fragment3),caller,courseId);
        onTitleChangeListener.onAddBackButton(getImageButton());
    }

    private @NonNull ImageButton getImageButton() {
        ImageButton imageButton = new ImageButton(requireContext());
        imageButton.setImageResource(R.drawable.back_arrow_icon);
        imageButton.setPadding(10, 10, 10, 10);
        imageButton.setBackgroundColor(getResources().getColor(R.color.black));
        imageButton.setLayoutParams(new LinearLayout.LayoutParams(
                50, // width in dp
                LinearLayout.LayoutParams.MATCH_PARENT // height
        ));
        imageButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return imageButton;
    }



}

