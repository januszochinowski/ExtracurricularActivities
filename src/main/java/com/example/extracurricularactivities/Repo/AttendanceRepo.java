package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Attendance;
import com.example.extracurricularactivities.Model.AttendanceKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceRepo extends JpaRepository<Attendance, AttendanceKey> {

    void deleteByStudentIdAndLessonId(long studentId, long lessonId);

    Optional<Attendance> findByStudentIdAndLessonId(long studentId, long lessonId);
}
