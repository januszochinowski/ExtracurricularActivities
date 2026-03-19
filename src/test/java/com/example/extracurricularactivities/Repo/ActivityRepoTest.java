package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.RandomUserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ActivityRepoTest {

    @Autowired
    private ActivityRepo repo;

    @Autowired
    private TeacherRepo teacherRepo;

    Activity activity;
    Teacher teacher;


    @BeforeEach
    void setUp() {
        teacher = RandomUserFactory.getRandomTeacher(false);
        teacherRepo.save(teacher);

        activity = RandomUserFactory.getRandomActivity(teacher);
        repo.save(activity);
    }

    @Test
    void getTeacherId() {
        assertEquals(teacher.getId(),repo.findTeacherId(activity.getId()));
    }
}