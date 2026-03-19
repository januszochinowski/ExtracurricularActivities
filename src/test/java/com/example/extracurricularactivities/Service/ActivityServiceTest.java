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

import java.lang.reflect.InvocationTargetException;

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
    void getActivityById() {
        assertEquals(activity, activityRepo.findById(activity.getId()).get());
    }

    @Test
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
}