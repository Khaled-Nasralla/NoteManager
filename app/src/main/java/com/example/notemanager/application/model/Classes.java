package com.example.notemanager.application.model;

import java.util.UUID;

public class Classes extends Inscrption {

    private  String nom;
    private UUID mId;
    private boolean inClass;

    public Classes(String nom, UUID uuid) {
        mId=uuid;
        this.nom = nom;

    }

    public String getNom() {
        return nom;
    }


    public void setId(UUID uuid) {
        mId = uuid;
    }

    @Override
    public boolean isInClass() {
        return true;
    }

    @Override
    public void setInClass(boolean inClass) {
        this.inClass = inClass;

    }

    @Override
    public String getClassName() {
        return nom;
    }

    public void setNom(String title) {
        nom = title;
    }

    public UUID getId() {
        return mId;
    }

    @Override
    public void setCourseId(UUID id) {
        mId=id;
    }

}
