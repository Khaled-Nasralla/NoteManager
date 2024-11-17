package com.example.notemanager.application.repository;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.notemanager.application.db.SchoolBaseHelper;
import com.example.notemanager.application.db.SchoolCursorWrapper;
import com.example.notemanager.application.db.SchoolDbSchema;
import com.example.notemanager.application.model.Student;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentRepository {
    private static StudentRepository sInstance;
    private final SQLiteDatabase mDatabase;
    private final MutableLiveData<List<Student>> mStudents;
    private final MutableLiveData<List<Student>> mStudentsLiveData;


    private StudentRepository(Application application) {
        Context context = application.getApplicationContext();
        mDatabase = new SchoolBaseHelper(context).getWritableDatabase();
        mStudents = new MutableLiveData<>();
        this.mStudentsLiveData = new MutableLiveData<>();
        loadStudents();
    }


    public static synchronized StudentRepository getInstance(Application application) {
        if (sInstance == null) {
            sInstance = new StudentRepository(application);
        }
        return sInstance;
    }

    private void loadStudents() {
        List<Student> students = new ArrayList<>();
        SchoolCursorWrapper cursor = queryStudents(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                students.add(cursor.getStudent());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        mStudents.postValue(students);
    }

    private  void loadStudentsinCourse(UUID courseId) {
        List<Student> students = new ArrayList<>();
        SchoolCursorWrapper cursor = queryStudentsinCourse(SchoolDbSchema.StudentTable.Cols.CLASS_ID + " = ?",
                new String[]{courseId.toString()});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                students.add(cursor.getStudent());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        mStudentsLiveData.setValue(students);
    }

    public LiveData<List<Student>> getStudentsForCourse(UUID courseId) {
        if (courseId != null) {
            loadStudentsinCourse(courseId);
        }
        return mStudentsLiveData;
    }
    private SchoolCursorWrapper queryStudents(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                SchoolDbSchema.StudentTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new SchoolCursorWrapper(cursor);
    }

    private SchoolCursorWrapper queryStudentsinCourse(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                SchoolDbSchema.StudentTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new SchoolCursorWrapper(cursor);
    }


    public LiveData<List<Student>> getStudents() {
        return mStudents;
    }

    public void addStudent(Student newStudent) {
        ContentValues values = getContentValues(newStudent);
        mDatabase.insert(SchoolDbSchema.StudentTable.NAME, null, values);
        loadStudents();
    }

    public void updateStudent(Student student) {
        String uuidString = student.getId().toString();
        ContentValues values = getContentValues(student);
        mDatabase.update(SchoolDbSchema.StudentTable.NAME, values,
                SchoolDbSchema.StudentTable.Cols.UUID + " = ?",
                new String[]{uuidString});
        loadStudents();
    }

    public void deleteStudent(Student student) {
        String uuidString = student.getId().toString();
        mDatabase.delete(SchoolDbSchema.StudentTable.NAME,
                SchoolDbSchema.StudentTable.Cols.UUID + " = ?",
                new String[]{uuidString});
        loadStudents();
    }

    public Student getStudent(UUID id) {
        SchoolCursorWrapper cursor = queryStudents(SchoolDbSchema.StudentTable.Cols.UUID + " = ?", new String[]{id.toString()});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getStudent();
        } finally {
            cursor.close();
        }
    }

    private  ContentValues getContentValues(Student student) {
        ContentValues values = new ContentValues();
        values.put(SchoolDbSchema.StudentTable.Cols.UUID, student.getId().toString());
        values.put(SchoolDbSchema.StudentTable.Cols.NAME, student.getName());
        values.put(SchoolDbSchema.StudentTable.Cols.SURNAME, student.getSurname());
        values.put(SchoolDbSchema.StudentTable.Cols.EMAIL, student.getEmail());
        values.put(SchoolDbSchema.StudentTable.Cols.CLASS_ID, student.getCourseId().toString());
        return values;
    }

    public void addStudentToCourse(Student student, UUID course) {
        ContentValues values = new ContentValues();
        values.put(SchoolDbSchema.StudentTable.Cols.CLASS_ID, course.toString());
        mDatabase.update(SchoolDbSchema.StudentTable.NAME, values,
                SchoolDbSchema.StudentTable.Cols.UUID + " = ?",
                new String[]{student.getId().toString()});
        student.setInClass(true);
        loadStudentsinCourse(course);
        loadStudents();

    }
}