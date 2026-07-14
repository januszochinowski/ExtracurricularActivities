package com.example.extracurricularactivities.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

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

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    List<Activity> activities;

    public void setPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        this.password = password;
    }
}
