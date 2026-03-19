package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Long> {

    <Optional> Activity getActivityById(Long id);

    @Query("SELECT a.teacher.id FROM Activity a JOIN  a.teacher WHERE a.id = :id")
    Long findTeacherId(long id);
}
