package com.example.extracurricularactivities.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Teacher extends User {

    String name;
    String surname;
    String phoneNumber;
    String email;
    Boolean isAdmin;
}
