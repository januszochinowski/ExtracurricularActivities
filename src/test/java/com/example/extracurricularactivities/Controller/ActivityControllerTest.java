package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.RandomUserFactory;
import com.example.extracurricularactivities.Repo.ActivityRepo;
import com.example.extracurricularactivities.Repo.TeacherRepo;
import com.example.extracurricularactivities.Service.ActivityService;
import com.example.extracurricularactivities.Service.JWTService;
import com.example.extracurricularactivities.Service.TeachersService;
import com.google.gson.Gson;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureRestTestClient
class ActivityControllerTest {

    @Autowired
    ActivityController controller;

    @Autowired
    JWTService jwtService;

    @Autowired
    TeacherRepo teacherRepo;

    @Autowired
    ActivityRepo activityRepo;

    @Autowired
    RestTestClient client;

    @Autowired
    TeachersService teachersService;

    String token;
    Teacher teacher;
    Activity activity;

    Gson gson =  new Gson();
    @Autowired
    private ActivityService activityService;

    @BeforeEach
    void setUp() {
        teacher = RandomUserFactory.getRandomTeacher(false);
       activity = RandomUserFactory.getRandomActivity(teacher);
       teachersService.addTeacher(teacher);
       activityService.addActivity(activity,teacher.getId());
    }

    @AfterEach
    void tearDown() {
        teacherRepo.deleteAll();
        activityRepo.deleteAll();
    }

    @Test
    void getActivityById() {
        client.get().uri("/activity?id=" + activity.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Activity.class)
                .isEqualTo(activity);
    }

    @Test
    void getActivitiesByTeacherId() {

    String response = client.get().uri("/activity?byTeacher="+teacher.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult().getResponseBody();

    assertTrue(response.contains(activity.getName()));

    }

    @Test
    void getActivitiesByName() {
    }

    @Test
    void getActivitiesByLocation() {
    }

    @Test
    void getActivitiesByDayOfWeek() {
    }

    @Test
    void updateActivity() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteActivity() {
    }
}