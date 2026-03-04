package com.example.extracurricularactivities.Model;

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

public class Teacher extends User {

    String name;
    String surname;
    String phoneNumber;
    String email;
    Boolean isAdmin;
}
