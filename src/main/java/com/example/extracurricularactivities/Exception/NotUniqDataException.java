package com.example.extracurricularactivities.Exception;

public class NotUniqDataException extends RuntimeException{

    @Override
    public String getMessage() {
        return "Same user already exist";
    }
}
