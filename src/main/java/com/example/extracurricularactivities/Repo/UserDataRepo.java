package com.example.extracurricularactivities.Repo;

import com.example.extracurricularactivities.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepo extends JpaRepository<Student,Long> {

    @Query("SELECT COUNT(u.id) FROM  Student u WHERE " +
            " LOWER(u.childName) LIKE LOWER( :name ) AND  LOWER(u.childSurname) LIKE LOWER(:surname)")
    int findSameUser(String name, String surname );


    void updateUserNameById(Long id, String name);
    void updateUserSurnameById(Long id, String surname);






}
