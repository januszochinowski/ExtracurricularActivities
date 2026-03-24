package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.RandomUserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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

    @Test
    @Transactional
    void findActivitiesByTeacherIdAfterOrStartTime() {
        Activity newActivity = RandomUserFactory.getRandomActivity(teacher);
        newActivity.setStartDate(LocalDate.now().plusDays(10));
        repo.save(newActivity);
        assertEquals( newActivity,repo.findActivitiesByTeacherIdAfterOrStartDate(teacher.getId(), PageRequest.of(0,10),LocalDate.now().plusDays(10)).getContent().get(0));
    }

    @Test
    void findActivitiesByNameAfterOrStartTime() {
    }

    @Test
    void findActivitiesByLocationAfterOrStartDate() {
    }
}