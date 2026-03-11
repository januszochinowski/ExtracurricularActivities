package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.Repo.TeacherRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MangeTeachersServiceTest {

    @Autowired
    private MangeTeachersService service;

    @Autowired
    private TeacherRepo repo;

    private Teacher teacher;
    private Teacher teacher2;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setName("Mange");
        teacher.setSurname("Surname");
        teacher.setEmail("gdhjakg");
        teacher.setPhoneNumber("hghjdagsk");
        teacher.setPassword("ghasgjs");
        teacher.setIsAdmin(false);
        teacher.setId(repo.save(teacher).getId());

        teacher2 = new  Teacher();
        teacher2.setName("Jan");
        teacher2.setSurname("Surname");
        teacher2.setEmail("gdhjakg");
        teacher2.setPhoneNumber("hghjdagsk");
        teacher2.setPassword("ghasgjs");
        teacher2.setIsAdmin(true);
        teacher2.setId(repo.save(teacher2).getId());
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
    }

    @Test
    void addTeacher() {
        Long id = service.addTeacher(teacher);
        assertEquals(teacher, repo.findById(id).get());
    }

    @Test
    void findTeacherById() {

        Teacher teacher2 = service.findTeacherById(teacher.getId()).get();
        assertEquals(teacher2,teacher);
    }

    @Test
    void deleteTeacherById() {
        service.deleteTeacherById(teacher.getId());
        assertTrue(service.findTeacherById(teacher.getId()).isEmpty());
    }

    @Test
    void getAllTeachers() {

        List<Teacher> teachers = service.getAllTeachers(2,0);
        assertEquals(teacher,teachers.get(0));
        assertEquals(teacher2,teachers.get(1));
    }

    @Test
    void getAllTeachersWithName() {
        teacher2.setName(teacher.getName());
        repo.save(teacher2);
        List<Teacher> teachers = service.getAllTeachersWithName(teacher.getName(),2,0);
        assertEquals(teacher,teachers.get(0));
        assertEquals(teacher2,teachers.get(1));
    }

    @Test
    void getAllTeachersWithSurname() {
        teacher2.setSurname(teacher.getSurname());
        repo.save(teacher2);
        List<Teacher> teachers = service.getAllTeachersWithSurname(teacher.getSurname(),2,0);
        assertEquals(teacher,teachers.get(0));
        assertEquals(teacher2,teachers.get(1));
    }

    @Test
    void update() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String newValue = "gjdhsakg";
        service.update(teacher.getId(),"Name",newValue);
        assertEquals(newValue,repo.findById(teacher.getId()).get().getName());

    }

    @Test
    void getAllAdmin() {
        assertEquals(teacher2,service.getAllAdmin(1,0).get(0));
    }
}