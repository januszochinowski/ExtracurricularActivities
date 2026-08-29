package com.example.extracurricularactivities.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;


import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student student)) return false;
        if (!super.equals(o)) return false;
        return childAge == student.childAge && Objects.equals(childName, student.childName) && Objects.equals(childSurname, student.childSurname) && Objects.equals(parentName, student.parentName) && Objects.equals(parentSurname, student.parentSurname) && Objects.equals(email, student.email) && Objects.equals(phoneNumber, student.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), childName, childSurname, childAge, parentName, parentSurname, email, phoneNumber);
    }
}
