package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.AttendanceList;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.RandomUserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class AttendanceListRepoTest {

    @Autowired
    private AttendanceListRepo attendanceListRepo;

    @Autowired
    private ActivityRepo activityRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    Teacher teacher;
    Activity activity;
    AttendanceList attendanceList;

    @BeforeEach
    void setUp() {
        teacher = RandomUserFactory.getRandomTeacher(false);
        teacherRepo.save(teacher);

        activity = RandomUserFactory.getRandomActivity(teacher);
        activityRepo.save(activity);

        attendanceList = new AttendanceList(activity, LocalDate.now());
        attendanceListRepo.save(attendanceList);
    }

    @AfterEach
    void tearDown() {
        attendanceListRepo.deleteAll();
        activityRepo.deleteAll();
        teacherRepo.deleteAll();
    }

    @Test
    void findByTeacherId() {
        assertEquals(attendanceList,attendanceListRepo.findByTeacherId(teacher.getId(), PageRequest.of(0,1)).getContent().get(0));
    }


    @Test
    void findByTeacherIdInDay() {
        LocalDate localDate = LocalDate.now().plusMonths(1);
        AttendanceList newAttendanceList = new AttendanceList(activity, localDate);
        attendanceListRepo.save(newAttendanceList);

        assertEquals(newAttendanceList,attendanceListRepo.findByTeacherIdInDay(teacher.getId(), PageRequest.of(0,2),localDate).getContent().get(0));

        assertNotEquals(attendanceList,attendanceListRepo.findByTeacherIdInDay(teacher.getId(), PageRequest.of(0,2),localDate).getContent().get(0));

    }
}