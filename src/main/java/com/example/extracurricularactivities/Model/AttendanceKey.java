package com.example.extracurricularactivities.Model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AttendanceKey implements Serializable {

    @Column(name=  "student_id" ,nullable = false)
    Long studentId;

    @Column(name= "lesson_id", nullable = false)
    Long lessonId;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AttendanceKey that)) return false;
        return Objects.equals(studentId, that.studentId) && Objects.equals(lessonId, that.lessonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, lessonId);
    }
}
