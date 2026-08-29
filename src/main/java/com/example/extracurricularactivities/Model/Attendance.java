package com.example.extracurricularactivities.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//Obecność
public class Attendance {

    @EmbeddedId
    AttendanceKey attendanceKey;

    @Column(nullable = false)
    Boolean isPresent;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name= "student_id", nullable = false)
    Student student;

    @ManyToOne
    @MapsId("lessonId")
    @JoinColumn(name = "lesson_id", nullable = false)
    Lesson lesson;


    public Attendance(Student student, Lesson lesson){
        this.student = student;
        this.lesson = lesson;
        isPresent = false;
        attendanceKey = new AttendanceKey(student.id,lesson.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Attendance that)) return false;
        return Objects.equals(attendanceKey, that.attendanceKey) && Objects.equals(isPresent, that.isPresent) && Objects.equals(student, that.student) && Objects.equals(lesson, that.lesson);
    }


    @Override
    public int hashCode() {
        return Objects.hash(attendanceKey, isPresent, student, lesson);
    }
}
