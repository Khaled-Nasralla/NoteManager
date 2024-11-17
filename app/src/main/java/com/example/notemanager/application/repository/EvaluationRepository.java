package com.example.notemanager.application.repository;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.MutableLiveData;

import com.example.notemanager.application.db.SchoolBaseHelper;
import com.example.notemanager.application.db.SchoolCursorWrapper;
import com.example.notemanager.application.db.SchoolDbSchema;
import com.example.notemanager.application.model.Evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EvaluationRepository {
    private static EvaluationRepository sInstance;
    private final SQLiteDatabase mDatabase;
    private final MutableLiveData<List<Evaluation>> mEvaluations;
    private final MutableLiveData<List<Evaluation>> mEvaluationsLiveData;

    private EvaluationRepository(Application application) {
        Context context = application.getApplicationContext();
        mDatabase = new SchoolBaseHelper(context).getWritableDatabase();
        mEvaluations = new MutableLiveData<>();
        this.mEvaluationsLiveData = new MutableLiveData<>();
        loadEvaluations();
    }

    public static synchronized EvaluationRepository getInstance(Application application) {
        if (sInstance == null) {
            sInstance = new EvaluationRepository(application);
        }
        return sInstance;
    }

    private void loadEvaluations() {
        List<Evaluation> evaluations = new ArrayList<>();
        SchoolCursorWrapper cursor = queryEvaluations(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                evaluations.add(cursor.getEvaluation());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        mEvaluations.postValue(evaluations);
    }

    private SchoolCursorWrapper queryEvaluations(String whereClause, String[] whereArgs) {

        Cursor cursor = mDatabase.query(
                SchoolDbSchema.EvaluationTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new SchoolCursorWrapper(cursor);
    }


    public MutableLiveData<List<Evaluation>> getEvaluations() {
        return mEvaluations;
    }


    public MutableLiveData<List<Evaluation>> getEvaluationsLiveData() {
        return mEvaluationsLiveData;
    }


    public void addEvaluation(Evaluation evaluation) {
        ContentValues values = getContentValues(evaluation);
        mDatabase.insert(SchoolDbSchema.EvaluationTable.NAME, null, values);
        loadEvaluations();
    }


public void updateEvaluationPointMax(UUID evaluationId, int newPointMax) {
    ContentValues values = new ContentValues();
    values.put(SchoolDbSchema.EvaluationTable.Cols.PointMax, newPointMax);
    mDatabase.update(SchoolDbSchema.EvaluationTable.NAME, values,
            SchoolDbSchema.EvaluationTable.Cols.UUID + " = ?",
            new String[]{evaluationId.toString()});
    loadEvaluations();
}


    public ContentValues getContentValues(Evaluation evaluation) {
        ContentValues values = new ContentValues();
        values.put(SchoolDbSchema.EvaluationTable.Cols.UUID, evaluation.getId().toString());
        values.put(SchoolDbSchema.EvaluationTable.Cols.TITLE, evaluation.getName());
        values.put(SchoolDbSchema.EvaluationTable.Cols.PointMax, evaluation.getPointMax());
        values.put(SchoolDbSchema.EvaluationTable.Cols.CLASS_ID, evaluation.getCourseId().toString());
        loadEvaluations();
        return values;
    }
}
