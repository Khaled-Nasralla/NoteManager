package com.example.notemanager.application.listener;

import android.widget.ImageButton;

import com.example.notemanager.application.model.Classes;
import com.example.notemanager.application.model.Course;
import com.example.notemanager.application.model.Evaluation;
import com.example.notemanager.application.model.Student;

import java.util.UUID;

public class Listener {
  public interface OnClassAddedListener {
    void onCourseAdded(Classes newCourse);
  }

  public interface OnStudentListener {
      void onStudentAdded(Student newStudent);
    void onAddStudentToCourse(Student student, UUID course);

  }


   public interface onCourseListener {
       void onCourseAdded(Course course, UUID classes);
    }

    public interface OnTitleChangeListener {
        void onFragmentsChange(String title,String caller, UUID courseId);
        void onAddBackButton(ImageButton imageButton);
        void onAddStudentButton(ImageButton imageButton);
    }


    public interface OnEvaluationAddedListener {
        void onEvaluationAdded(Evaluation evaluation);
    }
}
