package com.example.extracurricularactivities.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;


import java.time.LocalDate;

@Data
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
//Uczestnik
public class Student extends User {

    // child data
    @Column(nullable = false)
    private String childName;

    @Column(nullable = false)
    private String childSurname;

    @Column(nullable = false)
    private int childAge;

    //parent data
    @Column(nullable = false)
    private String parentName;

    @Column(nullable = false)
    private String parentSurname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

}
