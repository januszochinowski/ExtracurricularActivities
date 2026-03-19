package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.AttendanceList;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.RandomUserFactory;
import com.example.extracurricularactivities.Repo.ActivityRepo;
import com.example.extracurricularactivities.Repo.AttendanceListRepo;
import com.example.extracurricularactivities.Repo.TeacherRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@DataJpaTest
class AttendanceListServiceTest {

    @Autowired
    AttendanceListService service;

    @Autowired
    TeachersService teacherService;

    @Autowired
    ActivityService activityService;

    Teacher teacher;
    Activity activity;

    @BeforeEach
    void setUp() {
        teacher = RandomUserFactory.getRandomTeacher(false);
        activity = RandomUserFactory.getRandomActivity(teacher);
        activity.setStartDate(LocalDate.now().minusMonths(1));
        activity.setEndDate(LocalDate.now().plusMonths(1));
        teacherService.addTeacher(teacher);
        activityService.addActivity(activity,teacher.getId());
    }

    @Test
    void create() {
        service.create(activity);
    }

    @Test
    void delete() {
    }

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getAllMy() {
    }

    @Test
    void testGetAllMy() {
    }

    @Test
    void update() {
    }

    @Test
    void testUpdate() {
    }
}