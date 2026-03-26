package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.Lesson;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.RandomUserFactory;
import com.example.extracurricularactivities.Repo.LessonRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LessonServiceTest {

    @Autowired
    LessonService service;

    @Autowired
    TeachersService teacherService;

    @Autowired
    ActivityService activityService;

    @Autowired
    LessonRepo repo;

    Teacher teacher;
    Activity activity;

    @BeforeEach
    void setUp() {
        teacher = RandomUserFactory.getRandomTeacher(false);
        activity = RandomUserFactory.getRandomActivity(teacher);
        activity.setStartDate(LocalDate.now().minusMonths(1));
        activity.setEndDate(LocalDate.now().plusMonths(1));
        activity.setStartTime(LocalTime.now());
        teacherService.addTeacher(teacher);
        activityService.addActivity(activity,teacher.getId());
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
    }

    @Test
    @Transactional
    void create() {
        service.create(activity);
        assertEquals(activity,repo.findByTeacherIdInDay(teacher.getId(), PageRequest.of(0,1),activity.getStartDate()).getContent().get(0).getActivity());
        assertEquals(activity,repo.findByTeacherIdInDay(teacher.getId(), PageRequest.of(0,1),activity.getStartDate().plusDays(7)).getContent().get(0).getActivity());
        assertEquals(activity,repo.findByTeacherIdInDay(teacher.getId(), PageRequest.of(0,1),activity.getStartDate().plusDays(14)).getContent().get(0).getActivity());
        assertEquals(activity,repo.findByTeacherIdInDay(teacher.getId(), PageRequest.of(0,1),activity.getStartDate().plusDays(21)).getContent().get(0).getActivity());
    }

    @Test
    void delete() {
        service.delete(activity.getId());
        assertFalse(repo.findById(activity.getId()).isPresent());
    }

    @Test
    @Transactional
    void get() {
        Lesson lesson = new Lesson(activity,LocalDate.now());
        repo.save(lesson);
        assertEquals(lesson,service.get(lesson.getId()).get());
    }

    @Test
    @Transactional
    void getAll() {
        Lesson lesson = new Lesson(activity,LocalDate.now());
        Lesson lesson1 = new Lesson(activity,LocalDate.now());

        repo.save(lesson);
        repo.save(lesson1);

        assertArrayEquals( new Lesson[]{lesson, lesson1},service.getAll(0,3).toArray());

    }

    @Test
    @Transactional
    void getAllMyTeacher() {
        Teacher teacher1 = RandomUserFactory.getRandomTeacher(false);
        teacherService.addTeacher(teacher1);

        Activity newActivity = RandomUserFactory.getRandomActivity(teacher1);
        activityService.addActivity(newActivity,teacher1.getId());

        Lesson lesson = new Lesson(activity,LocalDate.now());
        Lesson lesson1 = new Lesson(newActivity,LocalDate.now());

        repo.save(lesson);
        repo.save(lesson1);

        assertArrayEquals( new Lesson[]{lesson},service.getAllMyTeacher(0,3,teacher.getId()).toArray());
        assertArrayEquals( new Lesson[]{lesson1}, service.getAllMyTeacher(0,3,teacher1.getId()).toArray());

    }

    @Test
    @Transactional
    void GetAllMyTeacherWithDate() {
        Teacher teacher1 = RandomUserFactory.getRandomTeacher(false);
        teacherService.addTeacher(teacher1);

        Activity newActivity = RandomUserFactory.getRandomActivity(teacher1);
        activityService.addActivity(newActivity,teacher1.getId());
        LocalDate date = LocalDate.now().plusDays(10);
        LocalDate date2 = LocalDate.now();
        Lesson lesson = new Lesson(activity,date);
        Lesson lesson1 = new Lesson(newActivity,date);
        Lesson lesson2 = new Lesson(activity,date2);

        repo.save(lesson);
        repo.save(lesson1);
        repo.save(lesson2);

        assertArrayEquals(new Lesson[]{lesson1},service.getAllMyTeacher(0,10,teacher1.getId(),date).toArray());
        assertArrayEquals(new Lesson[]{lesson2}, service.getAllMyTeacher(0,10,teacher.getId(),date2).toArray());

    }

    @Test
    @Transactional
    void update() {
        Lesson lesson = new Lesson(activity,LocalDate.now());
        repo.save(lesson);
        lesson.setIsCancelled(true);
        service.update(lesson);
        assertTrue(service.get(lesson.getId()).get().getIsCancelled());
    }

    @Test
    @Transactional
    void UpdateWithPartName() {
        Lesson lesson = new Lesson(activity,LocalDate.now());
        repo.save(lesson);

    }
}