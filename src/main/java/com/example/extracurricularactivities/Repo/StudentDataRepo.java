package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StudentDataRepo extends JpaRepository<Student,Long> {

    @Query("SELECT COUNT(u.id) FROM  Student u WHERE " +
            " LOWER(u.childName) = LOWER( :name ) AND  LOWER(u.childSurname) = LOWER(:surname)" +
            "AND u.childBirthDate = :birthDate ")
    int findSameStudent(String name, String surname, LocalDate birthDate );


    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student  s SET s.childName = :name WHERE s.id = :id ")
    void updateStudentChildNameById(Long id, String name);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student  s SET s.childSurname = :surname WHERE s.id = :id ")
    void updateStudentChildSurnameById(Long id, String surname);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student  s SET s.childBirthDate = :birthDate WHERE s.id = :id ")
    void updateStudentChildBirthDateById(Long id, LocalDate birthDate);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student  s SET s.parentName= :name WHERE s.id = :id ")
    void updateStudentParentNameById(Long id, String name);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student  s SET s.parentSurname= :surname WHERE s.id = :id ")
    void updateStudentParentSurnameById(Long id, String surname);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student  s SET s.email= :email WHERE s.id = :id ")
    void updateStudentEmail(Long id, String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student  s SET s.phoneNumber = :phoneNumber WHERE s.id = :id ")
    void updateStudentPhoneNumber(Long id, String phoneNumber);

    @Modifying(clearAutomatically = true)
    void deleteStudentById(Long id);









}
