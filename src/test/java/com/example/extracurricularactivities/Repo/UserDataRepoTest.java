package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserDataRepoTest {

    @Autowired
    private  UserDataRepo userDataRepo;

    private Student student;


    @BeforeEach
    void setUp(){
        student = new Student();
        student.setParentName("root");
        student.setParentSurname("root");
        student.setChildBirthDate(LocalDate.now());
        student.setChildSurname("John");
        student.setChildName("Kowalski");
        student.setEmail("john@poczta.pl");
        student.setPhoneNumber("1234567890");
        userDataRepo.save(student);
    }


    @Test
    void findById(){
      assertTrue( userDataRepo.findById((long) 1 ).isPresent());
      assertEquals(student,userDataRepo.findById((long) 1).get());
    }


    @Test
    void findSameUser() {
        assertEquals(1,userDataRepo.findSameUser(student.getChildName(),student.getChildSurname()));
    }


    @Test
    void updateUserById() {
        Student student2 = new Student();
        userDataRepo.updateUserNameById((long) 1, Pol);
        assertTrue(userDataRepo.findById((long) 2).isPresent());

    }
}