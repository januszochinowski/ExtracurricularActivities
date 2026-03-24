package com.example.extracurricularactivities.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
//Lista obecności
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendanceList_id")
    private Long id;

    @Column(nullable = false)
    LocalDate date;

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

    @OneToMany(mappedBy = "attendanceKey.lessonId")
    @JsonIgnore
    List<Attendance> attendances;

    public Lesson(Activity activity, LocalDate date){
        this.activity = activity;
        this.date = date;
        isCancelled = false;
        startTime = activity.getStartTime();
    }



}
