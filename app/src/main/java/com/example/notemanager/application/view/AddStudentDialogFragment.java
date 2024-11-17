// AddStudentDialogFragment.java
package com.example.notemanager.application.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notemanager.R;
import com.example.notemanager.application.listener.Listener;
import com.example.notemanager.application.model.Student;

import java.util.UUID;

public class AddStudentDialogFragment extends BaseDialogFragment {

    private EditText studentNameEditText;
    private EditText studentSurnameEditText;
    private Listener.OnStudentListener listener;

    public static AddStudentDialogFragment newInstance() {
        return new AddStudentDialogFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.add_student_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        studentNameEditText = view.findViewById(R.id.student_name);
        studentSurnameEditText = view.findViewById(R.id.student_surname);
        Button addButton = view.findViewById(R.id.submit_id);
        addButton.setOnClickListener(v -> {
            String studentName = studentNameEditText.getText().toString();
            String studentSurname = studentSurnameEditText.getText().toString();
            if (!studentName.isEmpty() && !studentSurname.isEmpty()) {
                Student newStudent = new Student(UUID.randomUUID(), studentName, studentSurname, UUID.randomUUID());
                if (listener != null) {
                    listener.onStudentAdded(newStudent);
                }
            }
            dismiss();
        });
    }

    public void setOnStudentAddedListener(Listener.OnStudentListener listener) {
        this.listener = listener;
    }
}