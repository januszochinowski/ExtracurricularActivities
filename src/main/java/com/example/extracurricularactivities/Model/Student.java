package com.example.extracurricularactivities.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;


import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // child data
    private String childName;
    private String childSurname;
    private LocalDate childBirthDate;

    //parent data
    private String parentName;
    private String parentSurname;
    private String email;
    private String phoneNumber;
}
