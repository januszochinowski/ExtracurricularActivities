package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Exception.NotUniqDataException;
import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Repo.StudentDataRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class StudentServiceTest {

    @Autowired
    private StudentDataRepo studentDataRepo;

    @Autowired
    private StudentService service;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setPassword("434531243");
        student.setParentName("root");
        student.setParentSurname("root");
        student.setChildAge(34);
        student.setChildName("Jan");
        student.setChildSurname("Kowalski");
        student.setEmail("dghjagd");
        student.setPhoneNumber("123456789");
        student.setId(studentDataRepo.save(student).getId());

    }

    @AfterEach
    void tearDown() {
        studentDataRepo.delete(student);
    }

    @Test
    void createStudent() {
        Student newStudent = new Student();
        newStudent.setPassword("1234");
        newStudent.setParentName("Jan");
        newStudent.setParentSurname("Kowalski");
        newStudent.setChildAge(13);
        newStudent.setChildName("Karl");
        newStudent.setChildSurname("Stoicki");
        newStudent.setEmail("hdjsahkj");
        newStudent.setPhoneNumber("31231231");

        service.createStudent(newStudent);
        assertEquals(1, studentDataRepo.findSameStudent(newStudent.getChildName(),newStudent.getChildSurname(),newStudent.getChildAge()));
        assertThrows(NotUniqDataException.class,() -> service.createStudent(newStudent));
        studentDataRepo.delete(newStudent);
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
        newStudent.setChildSurname("Kowalski");
        newStudent.setChildAge(14);
        newStudent.setParentSurname("Karl");
        newStudent.setParentName("Karl");
        newStudent.setEmail("gdsfad@hshaj");
        newStudent.setPhoneNumber("565323445");
        newStudent.setId(student.getId());
        newStudent.setPassword("12345");

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
        newStudent.setChildAge(10);
        newStudent.setParentSurname("Karl");
        newStudent.setParentName("Karl");
        newStudent.setEmail("gdsfad@hshaj");
        newStudent.setPhoneNumber("565323445");
        newStudent.setPassword("12345");
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
        newStudent.setChildAge(10);
        newStudent.setParentSurname("Karl");
        newStudent.setParentName("Karl");
        newStudent.setEmail("gdsfad@hshaj");
        newStudent.setPhoneNumber("565323445");
        newStudent.setPassword("12345");
        newStudent.setId(studentDataRepo.save(newStudent).getId());
        newStudent.setPassword("12345");
        assertThrows(NotUniqDataException.class,() -> service.updateChildSurname(student.getId(), newChildSurname));
    }

    @Test
    void isStudentNotUniq() {
        assertTrue(service.isStudentNotUniq(student));
        student.setChildSurname("fhas");
        assertFalse(service.isStudentNotUniq(student));

    }
}