package com.example.extracurricularactivities.Model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AttendanceKey implements Serializable {

    @Column(name=  "student_id" ,nullable = false)
    Long studentId;

    @Column(name= "lesson_id", nullable = false)
    Long lessonId;
}
