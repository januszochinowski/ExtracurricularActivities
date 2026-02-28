package com.example.extracurricularactivities.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {

    // child data
    private String childName;
    private String childSurname;
    private int childAge;

    //parent data
    private String parentName;
    private String parentSurname;
    private String email;
    private String phoneNumber;
}
