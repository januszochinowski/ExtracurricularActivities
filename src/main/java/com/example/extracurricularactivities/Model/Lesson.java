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
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
//Lista obecności
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Lesson_id")
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

    @OneToMany(mappedBy = "attendanceKey.lessonId",cascade = CascadeType.ALL)
    @JsonIgnore
    List<Attendance> attendances;

    public Lesson(Activity activity, LocalDate date){
        this.activity = activity;
        this.date = date;
        isCancelled = false;
        startTime = activity.getStartTime();
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Lesson lesson)) return false;
        return Objects.equals(id, lesson.id) && Objects.equals(date, lesson.date) && Objects.equals(isCancelled, lesson.isCancelled) && Objects.equals(startTime, lesson.startTime) && Objects.equals(activity, lesson.activity) && Objects.equals(substituteTeacher, lesson.substituteTeacher) && Objects.equals(attendances, lesson.attendances);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, date, isCancelled, startTime, activity, substituteTeacher, attendances);
    }
}
