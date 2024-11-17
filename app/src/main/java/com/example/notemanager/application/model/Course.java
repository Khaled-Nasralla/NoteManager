package com.example.notemanager.application.model;

import java.util.UUID;

public class Course  {

    private  String name;
    private UUID mId;
    private UUID classId;
    private int mCredit;

    public Course(String name, UUID uuid, UUID classId, int credit) {
        mId=uuid;
        this.name = name;
        this.classId = classId;
        this.mCredit = credit;

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

    public UUID getClassId() {
        return classId;
    }

    public void setClassId(UUID classId) {
        this.classId = classId;
    }

    public int getCredit() {
        return mCredit;
    }



}
