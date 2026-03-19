package com.example.extracurricularactivities.Exception;

public class AccessForbiddenActivity extends RuntimeException {
    public AccessForbiddenActivity( long id ) {
        super("Activity with id: " + id + "don't belongs to you" );
    }
}
