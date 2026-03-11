package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.Model.User;
import com.example.extracurricularactivities.RandomUserFactory;
import com.example.extracurricularactivities.Repo.StudentDataRepo;
import com.example.extracurricularactivities.Repo.TeacherRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureRestTestClient
class AdminControllerTest {

    private Student student;
    private Teacher teacher;
    private Teacher admin;

    @Autowired
    RestTestClient testClient;

    @Autowired
    StudentDataRepo studentRepo;

    @Autowired
    TeacherRepo teacherRepo;

    @BeforeEach
    void setUp() {
        student = RandomUserFactory.getRandomStudent();
        student.setId(studentRepo.save(student).getId());

        teacher = RandomUserFactory.getRandomTeacher(false);
        teacher.setId(teacherRepo.save(teacher).getId());

        admin = RandomUserFactory.getRandomTeacher(true);
        admin.setId(teacherRepo.save(admin).getId());


    }

    @AfterEach
    void tearDown() {
        studentRepo.deleteAll();
        teacherRepo.deleteAll();
    }

    @Test
    void getAllUser() {

        /* testClient.get().uri("/admin?size=2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                 .expectBody(List.class)
                .isEqualTo(List.of(student)); */

         teacher.setSurname("a" + teacher.getSurname());
         teacherRepo.save(teacher);
        testClient.get().uri("/admin?size=1&rule=teacher")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .isEqualTo(List.of(teacher));

        testClient.get().uri("/admin?page=1&size=1&rule=teacher")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .isEqualTo(List.of(admin));

        testClient.get().uri("/admin?size=2&rule=admin")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .isEqualTo(List.of(admin));

    }

    @Test
    void addTeacher() {
    }

    @Test
    void deleteTeacher() {
    }

    @Test
    void deleteStudent() {
    }
}