package com.example.notemanager.application.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notemanager.application.listener.Listener;
import com.example.notemanager.application.model.Course;
import com.example.notemanager.application.repository.CourseRepository;

import java.util.List;
import java.util.UUID;

public class CourseViewModel extends AndroidViewModel implements Listener.onCourseListener {
    private final CourseRepository courseRepository;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        courseRepository = CourseRepository.getInstance(application);

    }
    
    public void update(Course course) {
        courseRepository.update(course);
    }


    public LiveData<List<Course>> getAllCourses(UUID classId) {
        return courseRepository.getAllCourses(classId);
    }


    @Override
    public void onCourseAdded(Course course, UUID classes) {
        courseRepository.insert(course, classes);
    }
}
