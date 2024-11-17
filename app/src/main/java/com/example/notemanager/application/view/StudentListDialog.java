package com.example.notemanager.application.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.notemanager.R;
import com.example.notemanager.application.listener.Listener;
import com.example.notemanager.application.model.Student;
import com.example.notemanager.application.viewmodel.StudentsViewModel;

import java.util.UUID;

public class StudentListDialog extends BaseDialogFragment {

    private UUID courseId;
    private LinearLayout mStudentListLayout;
    private Listener.OnStudentListener listener;

    public StudentListDialog() {
    }

    public static StudentListDialog newInstance(UUID courseId) {
        StudentListDialog fragment = new StudentListDialog();
        Bundle args = new Bundle();
        args.putSerializable("courseId", courseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_student_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStudentListLayout = view.findViewById(R.id.student_list); // Initialize mStudentListLayout
        StudentsViewModel studentsViewModel = new ViewModelProvider(requireActivity()).get(StudentsViewModel.class);
        if (getArguments() != null) {
            courseId = (UUID) getArguments().getSerializable("courseId");
        }
        getStudentList(studentsViewModel);
        Button addButton = view.findViewById(R.id.submit_id);
        addButton.setOnClickListener(v -> {
            for (int i = 0; i < mStudentListLayout.getChildCount(); i++) {
                View studentView = mStudentListLayout.getChildAt(i);
                CheckBox checkBox = studentView.findViewById(R.id.student_checkbox);
                if (checkBox != null && checkBox.isChecked()) {
                    Student student = (Student) studentView.getTag();
                    listener.onAddStudentToCourse(student, courseId);
                }
            }
            dismiss();
        });
    }


    public void getStudentList(StudentsViewModel studentsViewModel) {
        studentsViewModel.getStudents().observe(getViewLifecycleOwner(), students -> {
            mStudentListLayout.removeAllViews();
            for (Student student : students) {
                if (!student.isInClass() && !student.getCourseId().equals(courseId)) {
                    mStudentListLayout.addView(getStudentView(student));
                }
            }
        });
    }

    public View getStudentView(Student student) {
        return studentLinearLayout(student);
    }


    @SuppressLint("SetTextI18n")
    public LinearLayout studentLinearLayout(Student student) {
        LinearLayout columnForStudent = new LinearLayout(requireContext());
        TextView studentName = new TextView(requireContext());
        CheckBox checkBox = new CheckBox(requireContext());
        checkBox.setId(R.id.student_checkbox); // Ensure the CheckBox has an ID
        checkBox.setPadding(8, 8, 8, 8);
        studentName.setText(student.getName() + " " + student.getSurname());
        columnForStudent.addView(checkBox);
        columnForStudent.addView(studentName);
        columnForStudent.setTag(student); // Set the student as a tag for later retrieval
        return columnForStudent;
    }

    public void setOnStudentAddedListener(Listener.OnStudentListener listener) {
        this.listener = listener;
    }
}