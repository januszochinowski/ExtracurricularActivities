package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Service.LessonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureRestTestClient
class AttendanceControllerTest {

    @Autowired
    AttendanceController attendanceController;

    @Autowired
    RestTestClient client;

    @BeforeEach
    void setUp() {
        
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() {
    }

    @Test
    void dropOutByStudent() {
    }

    @Test
    void dropOutByTeacher() {
    }

    @Test
    void markStudent() {
    }

    @Test
    void sinUp() {
    }
}