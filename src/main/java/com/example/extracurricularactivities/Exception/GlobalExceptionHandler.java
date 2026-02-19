package com.example.extracurricularactivities.Exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotUniqDataException.class, NullPointerException.class,NoSuchMethodException.class, InvocationTargetException.class, IllegalAccessException.class})
    public ResponseEntity<String> handleNotUniqDataException(NotUniqDataException e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<String> handleException(Exception e) {
        return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }







}
