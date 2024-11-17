package com.example.notemanager.application.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import com.example.notemanager.application.db.SchoolDbSchema.ClassTable;

public class SchoolBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "SchoolManagementBase.db";

    private static final String statementClass = "CREATE TABLE " + SchoolDbSchema.ClassTable.NAME + " (" +
            SchoolDbSchema.ClassTable.Cols.UUID + " TEXT PRIMARY KEY, " +
            SchoolDbSchema.ClassTable.Cols.TITLE + " TEXT" +
            ")";

    private static final String statementStudent = "CREATE TABLE " + SchoolDbSchema.StudentTable.NAME + " (" +
            SchoolDbSchema.StudentTable.Cols.UUID + " TEXT PRIMARY KEY, " +
            SchoolDbSchema.StudentTable.Cols.NAME + " TEXT, " +
            SchoolDbSchema.StudentTable.Cols.SURNAME + " TEXT, " +
            SchoolDbSchema.StudentTable.Cols.EMAIL + " TEXT, " +
            SchoolDbSchema.StudentTable.Cols.CLASS_ID + " TEXT, " +
            "FOREIGN KEY (" + SchoolDbSchema.StudentTable.Cols.CLASS_ID + ") REFERENCES " + SchoolDbSchema.ClassTable.NAME + "(" + SchoolDbSchema.ClassTable.Cols.UUID + ")" +
            ")";

    private static final String statementNote = "CREATE TABLE " + SchoolDbSchema.NoteTable.NAME + " (" +
            SchoolDbSchema.NoteTable.Cols.UUID + " TEXT PRIMARY KEY, " +
            SchoolDbSchema.NoteTable.Cols.STUDENT_ID + " TEXT, " +
            SchoolDbSchema.NoteTable.Cols.CLASS_ID + " TEXT, " +
            SchoolDbSchema.NoteTable.Cols.NOTE + " DOUBLE, " +
            "FOREIGN KEY (" + SchoolDbSchema.NoteTable.Cols.STUDENT_ID + ") REFERENCES " + SchoolDbSchema.StudentTable.NAME + "(" + SchoolDbSchema.StudentTable.Cols.UUID + "), " +
            "FOREIGN KEY (" + SchoolDbSchema.NoteTable.Cols.CLASS_ID + ") REFERENCES " + SchoolDbSchema.ClassTable.NAME + "(" + SchoolDbSchema.ClassTable.Cols.UUID + ")" +
            ")";

    private static final String statementEvaluation = "CREATE TABLE " + SchoolDbSchema.EvaluationTable.NAME + " (" +
            SchoolDbSchema.EvaluationTable.Cols.UUID + " TEXT PRIMARY KEY, " +
            SchoolDbSchema.EvaluationTable.Cols.TITLE + " TEXT, " +
            SchoolDbSchema.EvaluationTable.Cols.ParantId + " INTEGER, " +
            SchoolDbSchema.EvaluationTable.Cols.PointMax + " INTEGER, " +
            SchoolDbSchema.EvaluationTable.Cols.CLASS_ID + " TEXT, " +
            "FOREIGN KEY (" + SchoolDbSchema.EvaluationTable.Cols.CLASS_ID + ") REFERENCES " + SchoolDbSchema.ClassTable.NAME + "(" + SchoolDbSchema.ClassTable.Cols.UUID + ")," +
            "FOREIGN KEY (" + SchoolDbSchema.EvaluationTable.Cols.ParantId + ") REFERENCES " + SchoolDbSchema.EvaluationTable.NAME + "(" + SchoolDbSchema.EvaluationTable.Cols.UUID + ")" +
            ")";

    private static final String statementCourse = "CREATE TABLE " + SchoolDbSchema.CoursTable.NAME + " (" +
            SchoolDbSchema.CoursTable.Cols.UUID + " TEXT PRIMARY KEY, " +
            SchoolDbSchema.CoursTable.Cols.TITLE + " TEXT, " +
            SchoolDbSchema.CoursTable.Cols.CLASS_ID + " TEXT, " +
            SchoolDbSchema.CoursTable.Cols.CREDIT + " INTEGER, " +
            "FOREIGN KEY (" + SchoolDbSchema.CoursTable.Cols.CLASS_ID + ") REFERENCES " + SchoolDbSchema.ClassTable.NAME + "(" + SchoolDbSchema.ClassTable.Cols.UUID + ")" +
            ")";

    public SchoolBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;");
        db.execSQL(statementClass);
        db.execSQL(statementCourse);
        db.execSQL(statementEvaluation);
        db.execSQL(statementNote);
        db.execSQL(statementStudent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + ClassTable.NAME + " ADD COLUMN " + ClassTable.Cols.UUID);
        }
    }
}