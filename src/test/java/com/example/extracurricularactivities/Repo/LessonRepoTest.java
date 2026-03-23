package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.Lesson;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.RandomUserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class LessonRepoTest {

    @Autowired
    private LessonRepo lessonRepo;

    @Autowired
    private ActivityRepo activityRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    Teacher teacher;
    Activity activity;
    Lesson lesson;

    @BeforeEach
    void setUp() {
        teacher = RandomUserFactory.getRandomTeacher(false);
        teacherRepo.save(teacher);

        activity = RandomUserFactory.getRandomActivity(teacher);
        activityRepo.save(activity);

        lesson = new Lesson(activity, LocalDate.now());
        lessonRepo.save(lesson);
    }

    @AfterEach
    void tearDown() {
        lessonRepo.deleteAll();
        activityRepo.deleteAll();
        teacherRepo.deleteAll();
    }

    @Test
    void findByTeacherId() {
        assertEquals(lesson, lessonRepo.findByTeacherId(teacher.getId(), PageRequest.of(0,1)).getContent().get(0));
    }


    @Test
    void findByTeacherIdInDay() {
        LocalDate localDate = LocalDate.now().plusMonths(1);
        Lesson newLesson = new Lesson(activity, localDate);
        lessonRepo.save(newLesson);

        assertEquals(newLesson, lessonRepo.findByTeacherIdInDay(teacher.getId(), PageRequest.of(0,2),localDate).getContent().get(0));

        assertNotEquals(lesson, lessonRepo.findByTeacherIdInDay(teacher.getId(), PageRequest.of(0,2),localDate).getContent().get(0));

    }
}