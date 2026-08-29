package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.RandomUserFactory;
import com.example.extracurricularactivities.Repo.ActivityRepo;
import com.example.extracurricularactivities.Repo.LessonRepo;
import com.example.extracurricularactivities.Repo.TeacherRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.function.IntFunction;

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

    @Autowired
    LessonRepo lessonRepo;

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
        Activity newActivity = RandomUserFactory.getRandomActivity(teacher1);
        LocalDate startDate = LocalDate.of(2026, Month.FEBRUARY,1);
        LocalDate endDate = startDate.plusWeeks(10);
        LocalTime startTime = LocalTime.of(10,30);
        int duration = 60;

        newActivity.setStartDate(startDate);
        newActivity.setEndDate(endDate);
        newActivity.setStartTime(startTime);
        newActivity.setDuration(duration);

        activityService.addActivity(newActivity,teacher1.getId());

        assertTrue(activityRepo.findById(newActivity.getId()).isPresent());
        assertEquals(teacher2.getName(), activityRepo.findById(activity.getId()).get().getTeacher().getName());
        assertEquals(teacher2.getSurname(), activityRepo.findById(activity.getId()).get().getTeacher().getSurname());
        assertEquals(teacher2.getPhoneNumber(), activityRepo.findById(activity.getId()).get().getTeacher().getPhoneNumber());
        assertEquals(teacher2.getEmail(), activityRepo.findById(activity.getId()).get().getTeacher().getEmail());
        assertEquals(teacher2.getIsAdmin(), activityRepo.findById(activity.getId()).get().getTeacher().getIsAdmin());

        newActivity.setStartTime(startTime.plusMinutes(duration/2));

        assertThrows(EntityNotFoundException.class, () -> activityService.addActivity(newActivity,teacher1.getId()));

    }

    @Test
    void getActivityById() {
        assertEquals(activity.getId(), activityRepo.findById(activity.getId()).get().getId());
        assertEquals(activity.getName(), activityRepo.findById(activity.getId()).get().getName());
    }

    @Test
    void updateAll() {
        Activity newActivity = RandomUserFactory.getRandomActivity(teacher2);
        newActivity.setId(activity.getId());
        activityService.updateAll(newActivity,teacher1.getId());

        assertEquals(newActivity.getId(), activityRepo.findById(activity.getId()).get().getId());
        assertEquals(newActivity.getName(), activityRepo.findById(activity.getId()).get().getName());
        assertEquals(newActivity.getDescription(), activityRepo.findById(activity.getId()).get().getDescription());
        assertEquals(newActivity.getStartDate(),activityRepo.findById(activity.getId()).get().getStartDate());

        assertEquals(teacher2.getName(), activityRepo.findById(activity.getId()).get().getTeacher().getName());
        assertEquals(teacher2.getSurname(), activityRepo.findById(activity.getId()).get().getTeacher().getSurname());
        assertEquals(teacher2.getPhoneNumber(), activityRepo.findById(activity.getId()).get().getTeacher().getPhoneNumber());
        assertEquals(teacher2.getEmail(), activityRepo.findById(activity.getId()).get().getTeacher().getEmail());
        assertEquals(teacher2.getIsAdmin(), activityRepo.findById(activity.getId()).get().getTeacher().getIsAdmin());
    }

    @Test
    void deleteActivityById() {
        activityService.deleteActivityById(activity.getId(), teacher1.getId());
        assertFalse(activityRepo.findById(activity.getId()).isPresent());
    }

    @Test
    void update() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        activityService.update( new StringBuilder("name"),"Test", activity.getId(),teacher1.getId());
        assertEquals("Test", activityRepo.findById(activity.getId()).get().getName());
    }

    @Test
    void getActivitiesByTeacherId() {
        Activity newActivity = RandomUserFactory.getRandomActivity(teacher1);
        activityService.addActivity(newActivity,teacher1.getId());

        assertTrue(activityService.getActivitiesByTeacherId(teacher1.getId(),0,10).stream().anyMatch(a -> a.getId().equals(activity.getId())));
        assertTrue(activityService.getActivitiesByTeacherId(teacher1.getId(),0,10).stream().anyMatch(a -> a.getId().equals(newActivity.getId())));
        assertEquals(2,activityService.getActivitiesByTeacherId(teacher1.getId(),0,10).size());

        assertFalse(activityService.getActivitiesByTeacherId(teacher2.getId(),0,10).stream().anyMatch(a -> a.getId().equals(activity.getId())));
        assertFalse(activityService.getActivitiesByTeacherId(teacher2.getId(),0,10).stream().anyMatch(a -> a.getId().equals(newActivity.getId())));
        assertEquals(0,activityService.getActivitiesByTeacherId(teacher2.getId(),0,10).size());

    }

    @Test
    void getActivitiesByNameStartingWith() {

        assertEquals(activity.getId(), activityService.getActivitiesByNameStartingWith(activity.getName().substring(0,2),0,10).get(0).getId());
    }

    @Test
    void getActivitiesByLocationStartingWith() {
        assertEquals(activity.getId(), activityService.getActivitiesByLocationStartingWith(activity.getLocation(),0,10).get(0).getId());
    }

    @Test
    void getActivitiesByDayOfWeek() {
        Activity newActivity = RandomUserFactory.getRandomActivity(teacher1);
        newActivity.setStartDate(LocalDate.of(2026,3,23));
        activityService.addActivity(newActivity,teacher1.getId());
        assertEquals(newActivity.getId(),activityService.getActivitiesByDayOfWeek("monday",0,10).get(0).getId());
    }


    @Test
    void isTeacherFree() {
        activityService.addActivity(activity,teacher1.getId());

        assertFalse(activityService.isTeacherFree(teacher1.getId(),activity.getStartDate(),activity.getStartTime(),activity.getDuration()));
        assertFalse(activityService.isTeacherFree(teacher1.getId(),activity.getStartDate(),activity.getStartTime().plusMinutes(activity.getDuration()/2),activity.getDuration()));
        assertTrue(activityService.isTeacherFree(teacher1.getId(),activity.getStartDate(),activity.getStartTime().plusMinutes(activity.getDuration() + 1),activity.getDuration()));

    }


    @Test
    void getActivitiesByTeacherSurname() {
        assertEquals(activity.getId(),activityService.getActivitiesByTeacherSurname(teacher1.getSurname(),0,1).get(0).getId());
    }
}