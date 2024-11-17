package com.example.notemanager.application.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notemanager.application.listener.Listener;
import com.example.notemanager.application.model.Classes;
import com.example.notemanager.application.repository.ClassRepository;
import java.util.List;

public class ClassesViewModel extends AndroidViewModel implements Listener.OnClassAddedListener {
    private final ClassRepository mRepository;
    private final LiveData<List<Classes>> mCourses;


    public ClassesViewModel(@NonNull Application application) {
        super(application);
        mRepository = ClassRepository.getInstance(application);
        mCourses = mRepository.getCourses();
    }


    public LiveData<List<Classes>> getCourses() {
        return mCourses;
    }

    @Override
    public void onCourseAdded(Classes newCourse) {
        mRepository.addCourse(newCourse);
    }


}