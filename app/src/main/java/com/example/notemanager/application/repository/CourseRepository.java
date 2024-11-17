package com.example.notemanager.application.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notemanager.application.db.SchoolBaseHelper;
import com.example.notemanager.application.db.SchoolCursorWrapper;
import com.example.notemanager.application.db.SchoolDbSchema;
import com.example.notemanager.application.model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
// CourseRepository.java

public class CourseRepository {
    private static CourseRepository sInstance;
    private final SQLiteDatabase mDatabase;
    private final MutableLiveData<List<Course>> mCoursesLiveData;
    private UUID classId;

    public CourseRepository(Context context  ) {
        mDatabase = new SchoolBaseHelper(context.getApplicationContext()).getWritableDatabase();
        mCoursesLiveData = new MutableLiveData<>();

    }

    public static synchronized CourseRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CourseRepository(context);
        }
        return sInstance;
    }

    public LiveData<List<Course>> getAllCourses(UUID classId) {
        loadCourses(classId);
        return mCoursesLiveData;
    }

    public void insert(Course course, UUID classId) {
        ContentValues values = getContentValues(course, classId);
        mDatabase.insert(SchoolDbSchema.CoursTable.NAME, null, values);
        this.classId = classId;
        loadCourses(classId); // Refresh the LiveData
    }

    public void update(Course course) {
        // Update the course in the database
    }

    public void delete(Course course) {
        // Delete the course from the database
    }

    public void loadCourses(UUID classId) {
        List<Course> courses = new ArrayList<>();
        SchoolCursorWrapper cursor = queryCourses(SchoolDbSchema.CoursTable.Cols.CLASS_ID + " = ?", new String[]{classId.toString()});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                courses.add(cursor.getCourse());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        mCoursesLiveData.setValue(courses);
    }
//where classId  join with course table

    public SchoolCursorWrapper queryCourses(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                SchoolDbSchema.CoursTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new SchoolCursorWrapper(cursor);
    }

    private ContentValues getContentValues(Course course, UUID classId) {
        ContentValues values = new ContentValues();
        values.put(SchoolDbSchema.CoursTable.Cols.UUID, course.getId().toString());
        values.put(SchoolDbSchema.CoursTable.Cols.TITLE, course.getName());
        values.put(SchoolDbSchema.CoursTable.Cols.CLASS_ID, classId.toString());
        values.put(SchoolDbSchema.CoursTable.Cols.CREDIT, course.getCredit());
        loadCourses(classId);
        return values;
    }
}