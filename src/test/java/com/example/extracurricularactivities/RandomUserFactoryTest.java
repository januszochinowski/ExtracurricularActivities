package com.example.extracurricularactivities;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Model.Teacher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomUserFactoryTest {


    @Test
    void getRandomStudent() {
        Student student = RandomUserFactory.getRandomStudent();
        assertNotNull(student);
    }

    @Test
    void getRandomTeacher() {
        Teacher teacher = RandomUserFactory.getRandomTeacher(false);
        assertNotNull(teacher);
        assertFalse(teacher.getIsAdmin());

        Teacher admin = RandomUserFactory.getRandomTeacher(true);
        assertNotNull(admin);
        assertTrue(admin.getIsAdmin());
    }
}