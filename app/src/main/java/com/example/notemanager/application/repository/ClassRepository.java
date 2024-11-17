package com.example.notemanager.application.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.notemanager.application.db.SchoolBaseHelper;
import com.example.notemanager.application.db.SchoolCursorWrapper;
import com.example.notemanager.application.db.SchoolDbSchema;
import com.example.notemanager.application.model.Classes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class ClassRepository {
    private static ClassRepository sInstance;
    private final SQLiteDatabase mDatabase;
    private final MutableLiveData<List<Classes>> mCoursesLiveData;

    private ClassRepository(Context context) {
        mDatabase = new SchoolBaseHelper(context.getApplicationContext()).getWritableDatabase();
        mCoursesLiveData = new MutableLiveData<>();
        loadCourses();
    }

    public static synchronized ClassRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ClassRepository(context);
        }
        return sInstance;
    }

    private void loadCourses() {
        List<Classes> courses = new ArrayList<>();
        SchoolCursorWrapper cursor = queryCourses(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                courses.add(cursor.getClasses());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        mCoursesLiveData.setValue(courses);
    }

    public LiveData<List<Classes>> getCourses() {
        return mCoursesLiveData;
    }

    public void addCourse(Classes course) {
        ContentValues values = getContentValues(course);
        mDatabase.insert(SchoolDbSchema.ClassTable.NAME, null, values);
        loadCourses(); // Refresh the LiveData
    }

    private SchoolCursorWrapper queryCourses(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                SchoolDbSchema.ClassTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return new SchoolCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Classes course) {
        ContentValues values = new ContentValues();
        values.put(SchoolDbSchema.ClassTable.Cols.UUID, course.getId().toString());
        values.put(SchoolDbSchema.ClassTable.Cols.TITLE, course.getNom());
        return values;
    }



}