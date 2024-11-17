// AddCourseDialogFragment.java
package com.example.notemanager.application.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.notemanager.R;
import com.example.notemanager.application.listener.Listener;
import com.example.notemanager.application.model.Course;
import com.example.notemanager.application.viewmodel.CourseViewModel;

import java.util.UUID;

public class AddCourseDialogFragment extends BaseDialogFragment {

    private EditText courseNameEditText;
    private EditText courseCreditEditText;
    private Listener.onCourseListener listenerCourse;
    private UUID courseId;

    public static AddCourseDialogFragment newInstance(UUID courseId) {
        AddCourseDialogFragment fragment = new AddCourseDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("courseId", courseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.add_cours_dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseId = (UUID) getArguments().getSerializable("courseId");
        courseNameEditText = view.findViewById(R.id.course_title);
        courseCreditEditText = view.findViewById(R.id.course_credit);
        Button addButton = view.findViewById(R.id.submit_id);
        addButton.setOnClickListener(v -> {
            String courseName = courseNameEditText.getText().toString();
            int courseCredit = Integer.parseInt(courseCreditEditText.getText().toString());
            if (!courseName.isEmpty() || courseCredit != 0) {
                Course newCourse = new Course(courseName, UUID.randomUUID(), UUID.randomUUID(), courseCredit);
                if (listenerCourse != null) {
                    listenerCourse.onCourseAdded(newCourse, courseId);
                }
            }
            dismiss();
        });
    }

    // Inside CoursFragment.java



    public void setOnCourseAddedListener(CourseViewModel courseViewModel) {
        this.listenerCourse = courseViewModel;
    }
}