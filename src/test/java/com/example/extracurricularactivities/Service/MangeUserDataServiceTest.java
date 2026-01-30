package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Repo.StudentDataRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MangeUserDataServiceTest {

    @Autowired
    private StudentDataRepo studentDataRepo;

    @Autowired
    private MangeUserDataService service;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setParentName("root");
        student.setParentSurname("root");
        student.setChildBirthDate(LocalDate.now());
        student.setChildSurname("John");
        student.setChildName("Kowalski");
        student.setEmail("john@poczta.pl");
        student.setPhoneNumber("1234567890");
        student.setId(studentDataRepo.save(student).getId());

    }

    @AfterEach
    void tearDown() {
        studentDataRepo.deleteAll();
    }

    @Test
    void createUser() {
        Student newStudent = new Student();
        newStudent.setChildName("Karl");
        newStudent.setChildSurname("Karl");
        newStudent.setChildBirthDate(LocalDate.now());
        service.createUser(newStudent);
        assertEquals(1, studentDataRepo.findSameStudent(newStudent.getChildName(),newStudent.getChildSurname(),newStudent.getChildBirthDate()));
        assertThrows(NotUniqDataException.class,() -> service.createUser(newStudent));
        assertThrows(IllegalArgumentException .class,() -> service.createUser(null));

    }



    @Test
    void getStudentById() {
        assertEquals(student,service.getStudentById(student.getId()).get());
    }

    @Test
    void deleteStudentById() {
        service.deleteStudentById(student.getId());
        assertFalse(service.isStudentNotUniq(student));
    }

    @Test
    void updateStudent() {
        Student newStudent = new Student();
        newStudent.setChildName("Karl");
        newStudent.setChildSurname("Karl");
        newStudent.setChildBirthDate(LocalDate.now());
        newStudent.setParentSurname("Karl");
        newStudent.setParentName("Karl");
        newStudent.setEmail("gdsfad@hshaj");
        newStudent.setPhoneNumber("565323445");
        newStudent.setId(student.getId());

        service.updateStudent(student.getId(), newStudent);
        assertEquals(newStudent,service.getStudentById(student.getId()).get());

    }

    @Test
    void updateParentName() {
        String newParentName = "Karl";
        service.updateParentName(student.getId(), newParentName);
        assertEquals(newParentName,service.getStudentById(student.getId()).get().getParentName());
    }

    @Test
    void updateParentSurname() {
        String newParentSurname = "Karl";
        service.updateParentSurname(student.getId(), newParentSurname);
        assertEquals(newParentSurname,service.getStudentById(student.getId()).get().getParentSurname());

    }

    @Test
    void updateChildName() {
        String newChildName = "Jo";
        service.updateChildName(student.getId(), newChildName);
        assertEquals(newChildName,service.getStudentById(student.getId()).get().getChildName());

        Student newStudent = new Student();
        newStudent.setChildName(newChildName);
        newStudent.setChildSurname("Karl");
        newStudent.setChildBirthDate(LocalDate.now());
        newStudent.setParentSurname("Karl");
        newStudent.setParentName("Karl");
        newStudent.setEmail("gdsfad@hshaj");
        newStudent.setPhoneNumber("565323445");
        newStudent.setId(studentDataRepo.save(newStudent).getId());
        assertThrows(NotUniqDataException.class,() -> service.updateChildName(newStudent.getId(), newChildName));
    }

    @Test
    void updateChildSurname() {
        String newChildSurname = "dfgsfahgfJ";
        service.updateChildSurname(student.getId(), newChildSurname);
        assertEquals(newChildSurname,service.getStudentById(student.getId()).get().getChildSurname());

        Student newStudent = new Student();
        newStudent.setChildName("Karl");
        newStudent.setChildSurname(newChildSurname);
        newStudent.setChildBirthDate(LocalDate.now());
        newStudent.setParentSurname("Karl");
        newStudent.setParentName("Karl");
        newStudent.setEmail("gdsfad@hshaj");
        newStudent.setPhoneNumber("565323445");
        newStudent.setId(studentDataRepo.save(newStudent).getId());
        assertThrows(NotUniqDataException.class,() -> service.updateChildSurname(student.getId(), newChildSurname));
    }

    @Test
    void isStudentNotUniq() {
        assertTrue(service.isStudentNotUniq(student));
        student.setChildSurname("fhas");
        assertFalse(service.isStudentNotUniq(student));

    }
}