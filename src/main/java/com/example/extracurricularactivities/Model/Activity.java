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


}
