package com.example.notemanager.application.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.notemanager.application.listener.Listener;

import com.example.notemanager.application.model.Student;
import com.example.notemanager.application.repository.StudentRepository;

import java.util.List;
import java.util.UUID;


public class StudentsViewModel extends AndroidViewModel implements Listener.OnStudentListener {

    private StudentRepository mRepository;
    private final LiveData<List<Student>> mStudents;


    public StudentsViewModel(@NonNull Application application) {
        super(application);
        mRepository = StudentRepository.getInstance(application);
        mStudents = mRepository.getStudents();
    }

    public LiveData<List<Student>> getStudents() {
        return mStudents;
    }

    public LiveData<List<Student>> getStudentsForCourse(UUID courseId) {
        return mRepository.getStudentsForCourse(courseId);
    }


    @Override
    public void onStudentAdded(Student newStudent) {
        mRepository.addStudent(newStudent);
    }

    @Override
    public void onAddStudentToCourse(Student student, UUID course) {
        mRepository.addStudentToCourse(student, course);
    }



}
