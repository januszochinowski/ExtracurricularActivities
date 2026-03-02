package com.example.extracurricularactivities.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Attendance {

    @EmbeddedId
    AttendanceKey attendanceKey;

    Boolean isPresent;

    @ManyToOne
    @MapsId("student_id")
    @JoinColumn(name= "student_id", nullable = false)
    Student student;

    @ManyToOne
    @MapsId("attendanceList_id")
    @JoinColumn(name = "attendanceList_id", nullable = false)
    AttendanceList attendanceList;

}
