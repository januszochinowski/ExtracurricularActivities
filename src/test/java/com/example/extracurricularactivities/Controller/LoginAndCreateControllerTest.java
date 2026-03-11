package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Repo.StudentDataRepo;
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
class LoginAndCreateControllerTest {

    private Student student;

    @Autowired
    RestTestClient testClient;

    @Autowired
    StudentDataRepo repo;

    @BeforeEach
    void setUp() {

        student = new Student();
        student.setParentName("root");
        student.setParentSurname("root");
        student.setChildAge(10);
        student.setChildSurname("Jan");
        student.setChildName("Kowalski");
        student.setEmail("john@poczta.pl");
        student.setPhoneNumber("1234567890");
        student.setPassword("password");
        student.setId(repo.save(student).getId());
    }

    @AfterEach
    void tearDown() {
        repo.delete(student);
    }

    @Test
    void createStudent() {

        Student newStudent = new Student();
        newStudent.setPassword("password");
        newStudent.setChildName("Kasia");
        newStudent.setChildSurname("Walicki");
        newStudent.setEmail("hdsakj@dgasggkj");
        newStudent.setPhoneNumber("1234567890");

        Long id = testClient.post().uri("/create")
                .accept(MediaType.APPLICATION_JSON)
                .body(newStudent)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Long.class)
                .returnResult().getResponseBody();

        assertEquals(newStudent, repo.findById(id).get());
        repo.delete(newStudent);
    }

    @Test
    void userLogin() {


    }
}