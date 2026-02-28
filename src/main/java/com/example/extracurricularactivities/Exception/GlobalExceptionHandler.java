package com.example.extracurricularactivities.Exception;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotUniqDataException.class, NullPointerException.class,NoSuchMethodException.class, InvocationTargetException.class, IllegalAccessException.class, HibernateException.class})
    public ResponseEntity<String> handleNotUniqDataException(Exception e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityNotFoundException.class,UsernameNotFoundException.class})
    public ResponseEntity<String> handleException(Exception e) {
        return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadCredentialsException.class, SignatureException.class, SignatureException.class})
    public ResponseEntity<String> handleUnauthorizedException(Exception e) {
        return  new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }









}
