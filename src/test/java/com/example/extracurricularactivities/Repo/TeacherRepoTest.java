package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TeacherRepoTest {

    @Autowired
    private TeacherRepo repo;

    private Teacher teacher;
    private  Teacher teacher2;

    @BeforeEach
    void setUp() {
         teacher = new Teacher();
        teacher.setName("Jan");
        teacher.setSurname("surname");
        teacher.setEmail("hjagj@dasjg");
        teacher.setPassword("password");
        teacher.setIsAdmin(false);
        teacher.setPhoneNumber("gdhjgsaj");
        repo.save(teacher);

        teacher2 = new Teacher();
        teacher2.setName(teacher.getName() + "ek");
        teacher2.setSurname(teacher.getSurname() + "2");
        teacher2.setEmail("hjagj@dasjg");
        teacher2.setPassword("password2");
        teacher2.setIsAdmin(true);
        teacher2.setPhoneNumber("gdhjgsaj2");
        repo.save(teacher2);
    }

    @AfterEach
    void tearDown() {
        repo.delete(teacher);
        repo.delete(teacher2);
    }

    @Test
    void findTeachersByNameLike() {
        Pageable pageable = PageRequest.of(0,1);
        Page<Teacher> page = repo.findTeachersByNameStartingWith(teacher.getName(),pageable);
        assertEquals(1,page.getContent().size());
        assertEquals(teacher, page.getContent().get(0));

        pageable = page.nextOrLastPageable();
        page = repo.findTeachersByNameStartingWith(teacher.getName(),pageable);
        assertEquals(teacher2,page.getContent().get(0));

    }

    @Test
    void findTeachersBySurnameLike() {
        Pageable pageable = PageRequest.of(0,1);
        Page<Teacher> pages = repo.findTeachersBySurnameStartingWith(teacher.getSurname(),pageable);
        assertEquals(1,pages.getContent().size());
        assertEquals(teacher, pages.getContent().get(0));


        pages = repo.findTeachersBySurnameStartingWith(teacher2.getSurname(),pageable);
        assertEquals(1,pages.getContent().size());
        assertEquals(teacher2, pages.getContent().get(0));

    }

    @Test
    void findAllAdmin() {
        Pageable pageable = PageRequest.of(0,1);
        Page<Teacher> pages = repo.findAllAdmin(pageable);
        assertEquals(1,pages.getTotalElements());
        assertEquals(teacher2, pages.getContent().get(0));
    }

    @Test
    void getAllTeachers() {
        Pageable pageable = PageRequest.of(0,2);
        Page<Teacher> pages = repo.getAllTeachers(pageable);
        assertEquals(2,pages.getTotalElements());
        assertEquals(teacher,pages.getContent().get(0));
        assertEquals(teacher2,pages.getContent().get(1));
    }
}