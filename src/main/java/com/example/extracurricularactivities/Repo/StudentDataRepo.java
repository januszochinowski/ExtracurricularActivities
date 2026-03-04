package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDataRepo extends JpaRepository<Student,Long> {


    @Query("SELECT COUNT(u.id) FROM  Student u WHERE " +
            " LOWER(u.childName) = LOWER( :name ) AND  LOWER(u.childSurname) = LOWER(:surname)" +
            "AND u.childAge = :birthDate ")
    int findSameStudent(String name, String surname, int birthDate );


    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student  s SET s.childName = :name WHERE s.id = :id ")
    void updateStudentChildNameById(Long id, String name);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student  s SET s.childSurname = :surname WHERE s.id = :id ")
    void updateStudentChildSurnameById(Long id, String surname);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student  s SET s.childAge = :birthDate WHERE s.id = :id ")
    void updateStudentChildAge(Long id, int  birthDate);

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
    @Query("UPDATE Student s SET s.password = :newPassword WHERE s.id = :id")
    void updateStudentPassword(Long id, String newPassword);

    void deleteStudentById(Long id);


    Page<Student> findStudentByChildNameStartingWith(String name, Pageable pageable);

    Page<Student> findStudentByChildSurnameStartingWith(String surname, Pageable pageable);

    Page<Student> findStudentByParentSurnameStartingWith(String surname, Pageable pageable);

    Page<Student> findStudentByEmailStartingWith(String email, Pageable pageable);







}
