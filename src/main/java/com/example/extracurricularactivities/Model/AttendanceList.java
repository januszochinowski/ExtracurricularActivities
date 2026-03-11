package com.example.extracurricularactivities.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//Lista obecności
public class AttendanceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendanceList_id")
    private Long id;

    @Column(nullable = false)
    LocalDate startDate;

    @Column(nullable = false)
    Boolean isCancelled;

    @Column(nullable = false)
    LocalTime startTime;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    Activity activity;


    // can be null
    @ManyToOne
    @JoinColumn(name = "substitute_teacher_id")
    Teacher substituteTeacher;



}
