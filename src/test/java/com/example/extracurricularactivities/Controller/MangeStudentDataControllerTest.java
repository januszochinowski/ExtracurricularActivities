package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Repo.StudentDataRepo;
import com.example.extracurricularactivities.config.SecurityConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureDataSourceInitialization;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureRestTestClient
class MangeStudentDataControllerTest {

    private Student student;

    @Autowired
    private StudentDataRepo studentDataRepo;

    @Autowired
    private RestTestClient testClient;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setParentName("root");
        student.setParentSurname("root");
        student.setChildAge(10);
        student.setChildSurname("John");
        student.setChildName("Kowalski");
        student.setEmail("john@poczta.pl");
        student.setPhoneNumber("1234567890");
        student.setPassword("password");
        student.setId(studentDataRepo.save(student).getId());
    }

    @AfterEach
    void tearDown() {
      studentDataRepo.deleteAll();
    }

    @Test
    void createUser() {
        testClient.post().uri("/create")
                .body(student)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class);


    }

    @Test
    void getStudentData() {
        testClient.get().uri("/getStudentData")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Student.class)
                .isEqualTo(student);

    }

    @Test
    void updateStudentData() {
    }

    @Test
    void testUpdateStudentData() {
    }

    @Test
    void deleteStudentData() {
    }
}