package com.example.extracurricularactivities.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @MapsId("attendanceListId")
    @JoinColumn(name = "lesson_id", nullable = false)
    Lesson lesson;


    public Attendance(Student student, Lesson lesson){
        this.student = student;
        this.lesson = lesson;
    }
}
