package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Long> {

    <Optional> Activity getActivityById(Long id);

    @Query("SELECT a.teacher.id FROM Activity a JOIN  a.teacher WHERE a.id = :id")
    Long findTeacherId(long id);


    Page<Activity> findAll(Pageable pageable);
    Page<Activity> findActivitiesByTeacherId(Long teacherId, Pageable pageable);
    Page<Activity> findActivitiesByNameStartingWith(String name, Pageable pageable);
    Page<Activity> findActivitiesByLocationStartingWith(String name, Pageable pageable);

    @Query("SELECT  a FROM Activity  a WHERE a.startDate > :afterDate")
    Page<Activity> findAllAfterDate(Pageable pageable, LocalDate afterDate);
    Page<Activity> findActivitiesByTeacherIdAfterOrStartDate(Long teacherId, Pageable pageable, LocalDate AfterDate);

    @Query("SELECT a FROM Activity a  WHERE a.name LIKE %:name AND a.startDate > :afterDate")
    Page<Activity> findActivitiesByNameStartingWith(String name, Pageable pageable, LocalDate afterDate);

    @Query("SELECT a FROM Activity a  WHERE a.location LIKE %:name AND a.startDate > :afterDate")
    Page<Activity> findActivitiesByLocationStartingWith(String name, Pageable pageable,LocalDate afterDate );



}
