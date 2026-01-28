package com.example.extracurricularactivities.Service;

public class NotUniqDataException extends RuntimeException{

    @Override
    public String getMessage() {
        return "Same user already exist";
    }
}
