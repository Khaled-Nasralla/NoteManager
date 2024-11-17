package com.example.notemanager.application.model;

import java.util.UUID;

public class Evaluation {

    private  String name;
    private UUID mId;
    private Integer mPointMax = 20;
    private UUID courseId;


    public Evaluation(UUID uuid,String name, Integer pointMax, UUID courseId) {
        mId=uuid;
        this.name = name;
        mPointMax = pointMax;
        this.courseId = courseId;
    }

    public void setPointMax(int pointMax) {
        mPointMax = pointMax;
    }



    public Integer getPointMax() {
        return mPointMax;
    }


    public String getName() {
        return name;
    }


    public UUID getId(){
        return mId;
    }

    public void setId(UUID uuid) {
        mId = uuid;
    }


    public void setName(String title) {
        name = title;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }


}
