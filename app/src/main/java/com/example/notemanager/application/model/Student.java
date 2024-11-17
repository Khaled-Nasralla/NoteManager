package com.example.notemanager.application.model;

import java.util.UUID;

public class Student extends Inscrption {

private UUID mStudentId;
private String name;
private String surname;
private UUID mCourseId;
private boolean inClass;
private Classes mClasses;


    public Student(UUID uuid, String name, String surname, UUID courseId) {
        mStudentId = uuid;
        this.name = name;
        this.surname = surname;
        mCourseId = courseId;
    }


    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }
    public UUID getId() {
        return mStudentId;
    }


    public void setId(UUID id) {
        mStudentId = id;
    }


    @Override
    public boolean isInClass() {
        return inClass;
    }


    @Override
    public void setInClass(boolean inClass) {
        this.inClass = inClass;
    }

    @Override
    public String getClassName() {
        return mClasses.getNom();
    }


    public void setStudentId(UUID studentId) {
        mStudentId = studentId;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getEmail() {
        return "la" + mStudentId + "@student.helha.be";
    }

    public UUID getCourseId() {
        return mCourseId;
    }

    @Override
    public void setCourseId(UUID courseId) {
        mCourseId = courseId;
    }

    public String getMatricule() {
      return mStudentId.toString().substring(0, 8);
    }

}
