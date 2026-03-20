package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.AttendanceList;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.RandomUserFactory;
import com.example.extracurricularactivities.Repo.ActivityRepo;
import com.example.extracurricularactivities.Repo.AttendanceListRepo;
import com.example.extracurricularactivities.Repo.TeacherRepo;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AttendanceListServiceTest {

    @Autowired
    AttendanceListService service;

    @Autowired
    TeachersService teacherService;

    @Autowired
    ActivityService activityService;

    @Autowired
    AttendanceListRepo repo;

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
        AttendanceList attendanceList = new AttendanceList(activity,LocalDate.now());
        repo.save(attendanceList);
        assertEquals(attendanceList,service.get(attendanceList.getId()).get());
    }

    @Test
    @Transactional
    void getAll() {
        AttendanceList attendanceList = new AttendanceList(activity,LocalDate.now());
        AttendanceList attendanceList1 = new AttendanceList(activity,LocalDate.now());

        repo.save(attendanceList);
        repo.save(attendanceList1);

        assertArrayEquals( new AttendanceList[]{attendanceList,attendanceList1},service.getAll(0,3).toArray());

    }

    @Test
    @Transactional
    void getAllMy() {
        Teacher teacher1 = RandomUserFactory.getRandomTeacher(false);
        teacherService.addTeacher(teacher1);

        Activity newActivity = RandomUserFactory.getRandomActivity(teacher1);
        activityService.addActivity(newActivity,teacher1.getId());

        AttendanceList  attendanceList = new AttendanceList(activity,LocalDate.now());
        AttendanceList attendanceList1 = new AttendanceList(newActivity,LocalDate.now());

        repo.save(attendanceList);
        repo.save(attendanceList1);

        assertArrayEquals( new AttendanceList[]{attendanceList},service.getAllMy(0,3,teacher.getId()).toArray());
        assertArrayEquals( new AttendanceList[]{attendanceList1}, service.getAllMy(0,3,teacher1.getId()).toArray());

    }

    @Test
    @Transactional
    void GetAllMyWithDate() {
        Teacher teacher1 = RandomUserFactory.getRandomTeacher(false);
        teacherService.addTeacher(teacher1);

        Activity newActivity = RandomUserFactory.getRandomActivity(teacher1);
        activityService.addActivity(newActivity,teacher1.getId());
        LocalDate date = LocalDate.now().plusDays(10);
        LocalDate date2 = LocalDate.now();
        AttendanceList  attendanceList = new AttendanceList(activity,date);
        AttendanceList attendanceList1 = new AttendanceList(newActivity,date);
        AttendanceList attendanceList2 = new AttendanceList(activity,date2);

        repo.save(attendanceList);
        repo.save(attendanceList1);
        repo.save(attendanceList2);

        assertArrayEquals(new AttendanceList[]{attendanceList1},service.getAllMy(0,10,teacher1.getId(),date).toArray());
        assertArrayEquals(new AttendanceList[]{attendanceList2}, service.getAllMy(0,10,teacher.getId(),date2).toArray());

    }

    @Test
    void update() {
    }

    @Test
    void testUpdate() {
    }
}