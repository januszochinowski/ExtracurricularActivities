package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentDataRepoTest {

    @Autowired
    private StudentDataRepo studentDataRepo;

    private static Student student;


    @BeforeEach
    void setUp(){
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
    void cleanUp(){
        studentDataRepo.deleteAll();
    }


    @Test
    void findById(){
      assertTrue( studentDataRepo.findById(student.getId() ).isPresent());
      assertEquals(student, studentDataRepo.findById( student.getId()).get());
    }


    @Test
    void findSameStudent() {
        assertEquals(1, studentDataRepo.findSameStudent(student.getChildName(), student.getChildSurname(),student.getChildAge()));
    }




    @Test
    void updateStudentChildNameById() {
        String newName = "Karl";
        studentDataRepo.updateStudentChildNameById( student.getId(),newName);
        assertEquals(newName, studentDataRepo.findById( student.getId()).get().getChildName());
    }

    @Test
    void updateStudentChildSurnameById() {
        String newSurname = "Karl";
        studentDataRepo.updateStudentChildSurnameById( student.getId(),newSurname);
        assertEquals(newSurname, studentDataRepo.findById( student.getId()).get().getChildSurname());
    }

    @Test
    void updateStudentChildBirthdayById() {
        int newBirthDate = 15;
        studentDataRepo.updateStudentChildAge( student.getId(), newBirthDate);
        assertEquals(newBirthDate, studentDataRepo.findById( student.getId()).get().getChildAge());
    }

    @Test
    void updateStudentParentNameById() {
        String newName = "Joli";
        studentDataRepo.updateStudentParentNameById( student.getId(),newName);
        assertEquals(newName, studentDataRepo.findById( student.getId()).get().getParentName());
    }

    @Test
    void updateStudentParentSurnameById() {
        String newSurname = "Moli";
        studentDataRepo.updateStudentParentSurnameById( student.getId(),newSurname);
        assertEquals(newSurname, studentDataRepo.findById( student.getId()).get().getParentSurname());
    }

    @Test
    void updateStudentEmail() {
        String newEmail = "tom@dsad.pl";
        studentDataRepo.updateStudentEmail( student.getId(),newEmail);
        assertEquals(newEmail, studentDataRepo.findById( student.getId()).get().getEmail());
    }

    @Test
    void updateStudentPhoneNumber() {
        String newPhoneNumber = "555555555";
        studentDataRepo.updateStudentPhoneNumber( student.getId(),newPhoneNumber);
        assertEquals(newPhoneNumber, studentDataRepo.findById( student.getId()).get().getPhoneNumber());
    }

    @Test
    void deleteStudentById() {
        studentDataRepo.deleteById(student.getId());
        assertFalse( studentDataRepo.findById(student.getId()).isPresent());
    }

    @Test
    void updateStudentPassword() {
        String newPassword = "password2";
        studentDataRepo.updateStudentPassword( student.getId(),newPassword);
        assertEquals(newPassword, studentDataRepo.findById( student.getId()).get().getPassword());
    }

    @Test
    void findStudentByChildName() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Student> page = studentDataRepo.findStudentByChildNameStartingWith(student.getChildName().substring(0,1),pageable);
        assertEquals(student,page.getContent().get(0));
    }

    @Test
    void findStudentByChildSurname() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Student> page = studentDataRepo.findStudentByChildSurnameStartingWith(student.getChildSurname().substring(0,1),pageable);
        assertEquals(student,page.getContent().get(0));
    }

    @Test
    void findStudentByParentSurname() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Student> page = studentDataRepo.findStudentByParentSurnameStartingWith(student.getParentSurname().substring(0,1),pageable);
        assertEquals(student,page.getContent().get(0));
    }

    @Test
    void findStudentByEmail() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Student> page = studentDataRepo.findStudentByEmailStartingWith(student.getEmail().substring(0,1),pageable);
        assertEquals(student,page.getContent().get(0));
    }
}