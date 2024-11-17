package com.example.notemanager.application.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.notemanager.R;
import com.example.notemanager.application.listener.Listener;
import com.example.notemanager.application.model.Student;
import com.example.notemanager.application.viewmodel.StudentsViewModel;

import java.util.UUID;


public class StudentsFragment extends Fragment {

    private UUID courseId;
    private LinearLayout mStudentListLayout;
    private ImageButton imageButton;
    private final Listener.OnTitleChangeListener listener;


    public StudentsFragment(UUID courseId , Listener.OnTitleChangeListener listener) {
        this.courseId = courseId;
        this.listener = listener;
    }


    StudentsViewModel studentsViewModel;
    @SuppressLint("SetTextI18n")
    private View getStudentView(final Student student) {
        LinearLayout columnForStudent = getLinearLayout();
        TextView studentName = getTextView();
        TextView studentSurname = getTextView();
        TextView studentMatricule = getTextView();
        studentName.setText("Nom : "+ " "+student.getName());
        studentSurname.setText("Prénom : "+ " "+student.getSurname());
        studentMatricule.setText("Matricule : "+ " "+student.getMatricule());
        columnForStudent.addView(studentSurname);
        columnForStudent.addView(studentName);
        columnForStudent.addView(studentMatricule);
        return columnForStudent;
    }

    private @NonNull LinearLayout getLinearLayout() {
        LinearLayout columnForStudent = new LinearLayout(requireContext());
        columnForStudent.setOrientation(LinearLayout.VERTICAL);
        columnForStudent.setPadding(8, 8, 8, 8);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(10, 10, 10, 10);
        columnForStudent.setLayoutParams(params);
        columnForStudent.setBackgroundResource(R.drawable.border);
        columnForStudent.setBackgroundColor(getResources().getColor(R.color.greenblue));
        return columnForStudent;
    }

    @SuppressLint("RtlHardcoded")
    private @NonNull TextView getTextView() {
        TextView textView = new TextView(requireContext());
        textView.setTextSize(20);
        textView.setPadding(8, 8, 8, 8);
        textView.setGravity(Gravity.LEFT);
        return textView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        studentsViewModel.getStudentsForCourse(courseId).observe(getViewLifecycleOwner(), courses -> {
            mStudentListLayout.removeAllViews();
            for (Student student : courses) {
                    mStudentListLayout.addView(getStudentView(student));

            }
        });
    }

    private @NonNull ImageButton getAddStudentButton() {
        ImageButton button = new ImageButton(requireContext());
        button.setImageResource(R.drawable.add_student);
        button.setPadding(10, 10, 10, 10);
        button.setBackgroundColor(getResources().getColor(R.color.black));
        button.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // width in dp
                LinearLayout.LayoutParams.MATCH_PARENT // height
        ));
        button.setOnClickListener(v -> {
            AddStudentDialogFragment addStudentDialogFragment = new AddStudentDialogFragment();
            addStudentDialogFragment.setOnStudentAddedListener(studentsViewModel);
            addStudentDialogFragment.show(requireActivity().getSupportFragmentManager(), "AddStudentDialogFragment");
        });
        return button;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students, container, false);
        studentsViewModel = new ViewModelProvider(requireActivity()).get(StudentsViewModel.class);
        mStudentListLayout = view.findViewById(R.id.student_list);
        listener.onAddStudentButton(getAddStudentButton());
        return view;
    }
}