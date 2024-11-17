package com.example.notemanager.application.model;

import java.util.UUID;

public class SubEvaluation  {

public String mEvaluationName;

public SubEvaluation(UUID uuid, String name, String pointMax, String EvaluationName) {
        mEvaluationName=EvaluationName;
    }

    public String getEvaluationName() {
        return mEvaluationName;
    }

    public void setEvaluationName(String evaluationName) {
        mEvaluationName = evaluationName;
    }
}
