package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Attendance;
import com.example.extracurricularactivities.Model.AttendanceKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepo extends JpaRepository<Attendance, AttendanceKey> {

    void deleteByStudentIdAndLessonId(long studentId, long lessonId);

    Optional<Attendance> findByStudentIdAndLessonId(long studentId, long lessonId);
    List<Attendance> findAttendancesByStudentId(long studentId);
}
