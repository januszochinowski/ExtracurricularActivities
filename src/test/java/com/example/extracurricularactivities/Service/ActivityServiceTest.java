package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.RandomUserFactory;
import com.example.extracurricularactivities.Repo.ActivityRepo;
import com.example.extracurricularactivities.Repo.TeacherRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ActivityServiceTest {

    Teacher teacher1;
    Teacher teacher2;

    Activity activity;

    @Autowired
    ActivityService activityService;

    @Autowired
    TeachersService teacherService;

    @Autowired
    ActivityRepo activityRepo;

    @Autowired
    TeacherRepo teacherRepo;

    @BeforeEach
    void setUp() {
       teacher1 = RandomUserFactory.getRandomTeacher(false);
       teacher2 = RandomUserFactory.getRandomTeacher(false);
       teacherService.addTeacher(teacher1);
       teacherService.addTeacher(teacher2);

       activity = RandomUserFactory.getRandomActivity(teacher1);
       activityRepo.save(activity);
    }

    @AfterEach
    void tearDown() {
        activityRepo.deleteAll();
        teacherRepo.deleteAll();
    }

    @Test
    void addActivity() {
        Activity newActivity = RandomUserFactory.getRandomActivity(null);
        activityService.addActivity(newActivity,teacher2.getId());

        assertTrue(activityRepo.findById(newActivity.getId()).isPresent());
        assertEquals(teacher2, activityRepo.findById(activity.getId()).get().getTeacher());
    }

    @Test
    @Transactional
    void getActivityById() {
        assertEquals(activity, activityRepo.findById(activity.getId()).get());
    }

    @Test
    @Transactional
    void updateAll() {
        Activity newActivity = RandomUserFactory.getRandomActivity(teacher1);
        newActivity.setId(activity.getId());
        activityService.updateAll(newActivity,teacher1.getId());
        assertEquals(newActivity, activityRepo.findById(activity.getId()).get());
    }

    @Test
    void deleteActivityById() {
        activityService.deleteActivityById(activity.getId(), teacher1.getId());
        assertFalse(activityRepo.findById(activity.getId()).isPresent());
    }

    @Test
    void update() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        activityService.update( new StringBuilder("name"),"Test", activity.getId(),teacher1.getId());
        assertEquals(activity.getName(), activityRepo.findById(activity.getId()).get().getName());
    }

    @Test
    @Transactional
    void getActivitiesByTeacherId() {
        Activity newActivity = RandomUserFactory.getRandomActivity(teacher1);
        activityService.addActivity(newActivity,teacher1.getId());

        Activity newActivity2 = RandomUserFactory.getRandomActivity(teacher2);
        activityService.addActivity(newActivity2,teacher2.getId());

        assertArrayEquals(new Activity[]{activity,newActivity},activityService.getActivitiesByTeacherId(teacher1.getId(),0,10).toArray());
    }

    @Test
    @Transactional
    void getActivitiesByNameStartingWith() {

        assertEquals(activity, activityService.getActivitiesByNameStartingWith(activity.getName().substring(0,2),0,10).get(0));
    }

    @Test
    @Transactional
    void getActivitiesByLocationStartingWith() {

        assertEquals(activity, activityService.getActivitiesByLocationStartingWith(activity.getLocation(),0,10).get(0));
    }

    @Test
    @Transactional
    void getActivitiesByDayOfWeek() {
        Activity newActivity = RandomUserFactory.getRandomActivity(teacher1);
        newActivity.setStartDate(LocalDate.of(2026,3,23));
        activityService.addActivity(newActivity,teacher1.getId());
        assertEquals(newActivity,activityService.getActivitiesByDayOfWeek("monday",0,10).get(0));
    }
}