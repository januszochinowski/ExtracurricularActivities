package com.example.extracurricularactivities.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
//Prowadzący zajęcia
public class Teacher extends User {

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String surname;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    Boolean isAdmin;
}
