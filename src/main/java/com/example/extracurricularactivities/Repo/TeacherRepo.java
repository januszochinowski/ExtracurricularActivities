package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {

    public Page<Teacher> findTeachersByNameStartingWith(String name, Pageable pageable);


    public Page<Teacher> findTeachersBySurnameStartingWith(String surname, Pageable pageable);

    @Query("SELECT t FROM Teacher t WHERE t.isAdmin = TRUE")
    public Page<Teacher> findAllAdmin(Pageable pageable);

    @Query("SELECT t FROM Teacher t ")
    public Page<Teacher> getAllTeachers(Pageable pageable);












}
