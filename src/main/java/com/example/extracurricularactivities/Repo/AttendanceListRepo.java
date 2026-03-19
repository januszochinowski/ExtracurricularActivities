package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.AttendanceList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface AttendanceListRepo extends JpaRepository<AttendanceList, Long> {

    @Query("SELECT attList  FROM AttendanceList attList WHERE attList.activity IN (SELECT  a FROM Activity a WHERE a.teacher.id = :teacherId)")
    Page<AttendanceList> findByTeacherId(Long teacherId, Pageable pageable);

    @Query("SELECT attList  FROM AttendanceList attList WHERE (attList.activity IN (SELECT  a FROM Activity a WHERE a.teacher.id = :teacherId)) AND attList.date = :date ")
    Page<AttendanceList> findByTeacherIdInDay(Long teacherId, Pageable pageable, LocalDate date);
}
