package com.example.notemanager.application.model;

import java.util.UUID;

public abstract class Inscrption {
    public abstract UUID getId();
    public abstract void setCourseId(UUID id) ;
    public abstract boolean isInClass() ;
    public abstract void setInClass(boolean inClass) ;

    public abstract String getClassName();

}
