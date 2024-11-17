package com.example.notemanager.application.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notemanager.application.listener.Listener;
import com.example.notemanager.application.model.Evaluation;
import com.example.notemanager.application.repository.EvaluationRepository;

import java.util.List;
import java.util.UUID;

public class EvaluationViewModel extends AndroidViewModel implements Listener.OnEvaluationAddedListener {
    private final EvaluationRepository mEvaluationRepository;
    private final MutableLiveData<List<Evaluation>> mEvaluationsLiveData;

    public EvaluationViewModel(@NonNull Application application) {
        super(application);
        mEvaluationRepository = EvaluationRepository.getInstance(application);
        mEvaluationsLiveData = mEvaluationRepository.getEvaluationsLiveData();
    }

     public void addEvaluation(Evaluation evaluation) {

     }

        public void updateEvaluation(UUID evaluationId, int newPointMax) {
            mEvaluationRepository.updateEvaluationPointMax( evaluationId, newPointMax);
        }

        public LiveData<List<Evaluation>> getEvaluationsLiveData() {
            return mEvaluationsLiveData;
        }

    @Override
    public void onEvaluationAdded(Evaluation evaluation) {
        mEvaluationRepository.addEvaluation(evaluation);
    }
}
