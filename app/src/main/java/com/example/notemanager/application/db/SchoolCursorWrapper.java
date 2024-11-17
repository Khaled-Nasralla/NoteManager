package com.example.notemanager.application.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.notemanager.application.model.Classes;
import com.example.notemanager.application.model.Course;
import com.example.notemanager.application.model.Evaluation;
import com.example.notemanager.application.model.Student;

import java.util.UUID;

public class SchoolCursorWrapper extends CursorWrapper {
    public SchoolCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Classes getClasses() {
        String uuidString = getString(getColumnIndex(SchoolDbSchema.ClassTable.Cols.UUID));
        String title = getString(getColumnIndex(SchoolDbSchema.ClassTable.Cols.TITLE));

        Classes cours = new Classes(title, UUID.fromString(uuidString));
        cours.setId(UUID.fromString(uuidString));
        cours.setNom(title);

        return cours;
    }

    public Student getStudent() {
        String uuidString = getString(getColumnIndex(SchoolDbSchema.StudentTable.Cols.UUID));
        String name = getString(getColumnIndex(SchoolDbSchema.StudentTable.Cols.NAME));
        String surname = getString(getColumnIndex(SchoolDbSchema.StudentTable.Cols.SURNAME));
        String classId = getString(getColumnIndex(SchoolDbSchema.StudentTable.Cols.CLASS_ID));
        Student student = new Student(UUID.fromString(uuidString), name, surname, UUID.fromString(classId));
        student.setName(name);
        student.setSurname(surname);
        student.setStudentId(UUID.fromString(uuidString));
        student.setCourseId(UUID.fromString(classId));

        return student;
    }

    public Course getCourse() {
        String uuidString = getString(getColumnIndex(SchoolDbSchema.CoursTable.Cols.UUID));
        String title = getString(getColumnIndex(SchoolDbSchema.CoursTable.Cols.TITLE));
        String classId = getString(getColumnIndex(SchoolDbSchema.CoursTable.Cols.CLASS_ID));
        String credit = getString(getColumnIndex(SchoolDbSchema.CoursTable.Cols.CREDIT));
        Course course = new Course( title,UUID.fromString(uuidString), UUID.fromString(classId), Integer.parseInt(credit));
        course.setId(UUID.fromString(uuidString));
        course.setName(title);
        course.setClassId(UUID.fromString(classId));

        return course;
    }

    public Evaluation getEvaluation() {
        String uuidString = getString(getColumnIndex(SchoolDbSchema.EvaluationTable.Cols.UUID));
        String title = getString(getColumnIndex(SchoolDbSchema.EvaluationTable.Cols.TITLE));
        int pointMax = getInt(getColumnIndex(SchoolDbSchema.EvaluationTable.Cols.PointMax));
        String classId = getString(getColumnIndex(SchoolDbSchema.EvaluationTable.Cols.CLASS_ID));
        Evaluation evaluation = new Evaluation(UUID.fromString(uuidString), title, pointMax,UUID.fromString(classId));
        evaluation.setId(UUID.fromString(uuidString));
        evaluation.setName(title);
        evaluation.setCourseId(UUID.fromString(classId));
        evaluation.setPointMax(pointMax);
        return evaluation;
    }
}