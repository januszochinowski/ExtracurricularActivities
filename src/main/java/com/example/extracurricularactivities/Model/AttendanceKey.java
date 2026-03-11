package com.example.extracurricularactivities.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class AttendanceKey implements Serializable {

    @Column(name=  "student_id" ,nullable = false)
    Long studentId;

    @Column(name= "attendanceList_id", nullable = false)
    Long attendanceListId;
}
