package com.example.extracurricularactivities.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Teacher teacher)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, teacher.name) && Objects.equals(surname, teacher.surname) && Objects.equals(phoneNumber, teacher.phoneNumber) && Objects.equals(email, teacher.email) && Objects.equals(isAdmin, teacher.isAdmin) && Objects.equals(activities, teacher.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, phoneNumber, email, isAdmin, activities);
    }
}
