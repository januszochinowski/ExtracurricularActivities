package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.RandomUserFactory;
import com.example.extracurricularactivities.Repo.StudentDataRepo;
import com.example.extracurricularactivities.Service.JWTService;
import com.example.extracurricularactivities.Service.MangeStudentDataService;
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
    private String token;

    @Autowired
    private StudentDataRepo studentDataRepo;

    @Autowired
    private RestTestClient testClient;

    @Autowired
    private MangeStudentDataService studentService;

    @Autowired
    private JWTService jwtService;



    @BeforeEach
    void setUp() {
        student = RandomUserFactory.getRandomStudent();
        studentService.createStudent(student);
        token =  jwtService.generateToken(student.getId().toString());

    }

    @AfterEach
    void tearDown() {
      studentDataRepo.deleteAll();
    }



    @Test
    void getStudentData() {
        testClient.get().uri("/studentData")
                .header("Authorization","Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Student.class)
                .isEqualTo(student);

    }

    @Test
    void updateStudentData() {
        student.setChildName("Child Name");
        testClient.put().uri("/studentData")
                .header("Authorization","Bearer " + token)
                .body(student)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Student updated");
    }

    @Test
    void patchUpdateStudentData() {
        String newName = "Robert";
        testClient.patch().uri("/studentData/ChildName?value="+newName)
                .header("Authorization","Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Student ChildName updated");

        assertEquals(newName,studentDataRepo.findById(student.getId()).get().getChildName());

        newName = "Kamil";
        testClient.patch().uri("/studentData/childName?value="+newName)
                .header("Authorization","Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Student ChildName updated");

        assertEquals(newName,studentDataRepo.findById(student.getId()).get().getChildName());



    }

    @Test
    void deleteStudentData() {

        testClient.delete().uri("/studentData")
                .header("Authorization","Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Student deleted");
        assertTrue(studentDataRepo.findById(student.getId()).isEmpty());

    }
}