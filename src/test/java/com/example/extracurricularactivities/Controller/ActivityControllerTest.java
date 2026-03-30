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
import org.junit.jupiter.api.Assertions;
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
    Activity activity2;

    Gson gson =  new Gson();
    @Autowired
    private ActivityService activityService;

    @BeforeEach
    void setUp() {
        teacher = RandomUserFactory.getRandomTeacher(false);
       activity = RandomUserFactory.getRandomActivity(teacher);
       activity2 =RandomUserFactory.getRandomActivity(teacher);
       teachersService.addTeacher(teacher);
       activityService.addActivity(activity,teacher.getId());
       activityService.addActivity(activity2,teacher.getId());

       token = jwtService.generateToken(teacher.getId().toString());
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
                .value( a -> {
                    assertEquals(a.getId(), activity.getId());
                });
    }

    @Test
    void getActivitiesByTeacherId() {

    client.get().uri("/activity/byTeacher?id="+teacher.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value( s ->
                        {
                                assertTrue( s.contains(activity.getName()));
                                assertTrue(s.contains(activity2.getName()));
                        }
                );


    }

    @Test
    void getActivitiesByName() {

        client.get().uri("/activity/byName?name="+activity.getName())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value( response -> assertTrue(response.contains(activity.getId().toString())));

        client.get().uri("/activity/byName?name="+activity.getName()+"afterDate="+activity.getStartDate().minusDays(1))
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value( response -> assertTrue(response.contains(activity.getId().toString())));


    }

    @Test
    void getActivitiesByLocation() {
        client.get().uri("/activity/byLocation?location="+activity.getLocation())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value( response -> assertTrue(response.contains(activity.getId().toString())));

        client.get().uri("/activity/byLocation?location="+activity.getLocation()+"&afterDate="+activity.getStartDate().minusDays(1))
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value( response -> assertTrue(response.contains(activity.getId().toString())));


    }

    @Test
    void getActivitiesByDayOfWeek() {
        client.get().uri("/activity/byDayOfWeek?day="+activity.getStartDate().getDayOfWeek())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value( response -> assertTrue(response.contains(activity.getId().toString())));

        client.get().uri("/activity/byDayOfWeek?day="+activity.getStartDate().getDayOfWeek()+"&afterDate="+activity.getStartDate().minusDays(1))
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value( response -> assertTrue(response.contains(activity.getId().toString())));
    }

    @Test
    void updateActivity() {
        activity.setLocation("Updated location");

        client.put().uri("/activity")
                .header("Authorization", "Bearer " + token)
                .body(activity)
                .exchange()
                .expectStatus().isOk();

        assertEquals(activity.getLocation(), activityRepo.findById(activity.getId()).get().getLocation());
    }

    @Test
    void update() {
        client.patch().uri("/activity?part=name&value=updated&id="+activity.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk();

        client.get().uri("/activity?id="+activity.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Activity.class)
                .value(Assertions::assertNotNull)
                .value( a -> assertEquals("update", a.getLocation()));

    }

    @Test
    void deleteActivity() {
        client.delete().uri("/activity?id="+activity.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk();

        client.get().uri("/activity?id="+activity.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isNotFound();

    }
}