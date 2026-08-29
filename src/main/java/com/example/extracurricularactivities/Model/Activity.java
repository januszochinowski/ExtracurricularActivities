package com.example.extracurricularactivities.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity
//Zajęcia
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    int minAge;

    @Column(nullable = false)
    int maxAge;

    @Column(nullable = false)
    LocalDate startDate;

    @Column(nullable = false)
    LocalDate endDate;

    @Column(nullable = false)
    int duration;

    @Column(nullable = false)
    int maxNumberOfStudents;

    @Column(nullable = false)
    LocalTime startTime;

    @ManyToOne
    @JoinColumn(nullable = false, name = "teacher_id")
    Teacher teacher;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    List<Lesson> lesson;

    @JsonCreator
    public Activity(String name, String description, String  location, int minAge, int maxAge, LocalDate startDate, LocalDate endDate, int duration) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        teacher = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return minAge == activity.minAge && maxAge == activity.maxAge && duration == activity.duration && maxNumberOfStudents == activity.maxNumberOfStudents && Objects.equals(id, activity.id) && Objects.equals(name, activity.name) && Objects.equals(description, activity.description) && Objects.equals(location, activity.location) && Objects.equals(startDate, activity.startDate) && Objects.equals(endDate, activity.endDate) && Objects.equals(startTime, activity.startTime) && Objects.equals(teacher, activity.teacher) && Objects.equals(lesson, activity.lesson);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, location, minAge, maxAge, startDate, endDate, duration, maxNumberOfStudents, startTime, teacher, lesson);
    }
}
