package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.Model.User;
import com.example.extracurricularactivities.RandomUserFactory;
import com.example.extracurricularactivities.Repo.StudentDataRepo;
import com.example.extracurricularactivities.Repo.TeacherRepo;
import com.example.extracurricularactivities.Service.JWTService;
import com.example.extracurricularactivities.Service.MangeStudentDataService;
import com.example.extracurricularactivities.Service.MangeTeachersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
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


    private String adminToken;

    @Autowired
    RestTestClient testClient;

    @Autowired
    MangeTeachersService  teacherService;

    @Autowired
    MangeStudentDataService studentService;

    @Autowired
    TeacherRepo teacherRepo;

    @Autowired
    StudentDataRepo studentRepo;

    @Autowired
    JWTService  jwtService;




    @BeforeEach
    void setUp() {
        student = RandomUserFactory.getRandomStudent();
        studentService.createStudent(student);

        teacher = RandomUserFactory.getRandomTeacher(false);
        teacherService.addTeacher(teacher);

        admin = RandomUserFactory.getRandomTeacher(true);
        teacherService.addTeacher(admin);
        adminToken = jwtService.generateToken(admin.getId().toString());





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
        testClient.get().uri("/admin?size=1&role=teacher")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .returnResult().getResponseBody();

        testClient.get().uri("/admin?page=1&size=1&rule=teacher")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .isEqualTo(List.of(admin));

        testClient.get().uri("/admin?size=2&rule=admin")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .isEqualTo(List.of(admin));


        testClient.get().uri("/admin?size=1&rule=teacher")
                .header("Authorization", "Bearer " + jwtService.generateToken(teacher.getId().toString()))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);

    }

    @Test
    void addTeacher() {
        teacher = RandomUserFactory.getRandomTeacher(false);
        String id= testClient.post().uri("/admin/addTeacher")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body(teacher)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .returnResult().getResponseBody();

        assertEquals(teacher,teacherService.getTeacherById(Long.valueOf(id)).get()) ;
    }

    @Test
    void deleteTeacher() {
        testClient.delete().uri("/admin/teacher?id=" + teacher.getId())
                .header("Authorization", "Bearer " + adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Teacher successfully deleted ");

        assertFalse(teacherRepo.findById(teacher.getId()).isPresent());

    }

    @Test
    void deleteStudent() {
        testClient.delete().uri("/admin/student?id=" + student.getId())
                .header("Authorization", "Bearer " + adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Student successfully deleted ");

        assertFalse(studentRepo.findById(teacher.getId()).isPresent());

    }
}