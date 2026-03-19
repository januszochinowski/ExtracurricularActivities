package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.RandomUserFactory;
import com.example.extracurricularactivities.Repo.TeacherRepo;
import com.example.extracurricularactivities.Service.JWTService;
import com.example.extracurricularactivities.Service.TeachersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureRestTestClient
class TeacherControllerTest {

    private Teacher teacher;
    private String token;

    @Autowired
    private TeachersService service;

    @Autowired
    private RestTestClient client;

    @Autowired
    private JWTService  jwtService;

    @Autowired
    private TeacherRepo  repo;



    @BeforeEach
    void setUp() {
        teacher = RandomUserFactory.getRandomTeacher(false);
        service.addTeacher(teacher);
        token = jwtService.generateToken(teacher.getId().toString());

    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
    }

    @Test
    void getById() {
        client.get().uri("/teacher")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Teacher.class)
                .isEqualTo(teacher);
    }

    @Test
    void deleteById() {
        client.delete().uri("/teacher")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer " + token)
                .exchange()
                .expectStatus().isOk();

        assertFalse(repo.findById(teacher.getId()).isPresent());
    }

    @Test
    void updateTeacher() {
        String newName = "Ala";
        client.patch().uri("/teacher?part=name&value="+newName)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer " + token)
                .exchange()
                .expectStatus().isOk();
        assertEquals(newName, repo.findById(teacher.getId()).get().getName());
    }
}