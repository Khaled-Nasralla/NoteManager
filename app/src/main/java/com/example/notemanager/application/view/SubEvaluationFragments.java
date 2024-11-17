package com.example.notemanager.application.view;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.notemanager.R;
import com.example.notemanager.application.listener.Listener;
import com.example.notemanager.application.model.SubEvaluation;

import java.util.UUID;

public class SubEvaluationFragments extends Fragment {

    UUID mCourseId;
    Listener.OnTitleChangeListener mListener;


  public SubEvaluationFragments(UUID courseId) {
      this.mCourseId=courseId;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_sub_evaluation, container, false);

    }





}
