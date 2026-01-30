package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Repo.StudentDataRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional
@Service
public class MangeUserDataService {


    private StudentDataRepo studentDataRepo;

    public MangeUserDataService(StudentDataRepo studentDataRepo) {
        this.studentDataRepo = studentDataRepo;
    }


    /**
     *  Saving new student in database
     * @param student
     * @return true if saved
     */
    @Transactional
    public void createUser(Student student){

        if(isStudentNotUniq(student))  throw new NotUniqDataException();
        studentDataRepo.save(student);
    }


    /**
     * Check if there is  another student with the same details: childName, childSurname, childBirthDate (all 3)
     * @param student
     * @return true when the same student exist in database
     */
    public boolean isStudentNotUniq(String childName,String childSurname, String childBirthDate){
       return  studentDataRepo.findSameStudent(student.getChildName(), student.getChildSurname(),student.getChildBirthDate()) >  0;
    }


    /**
     * Find student by id
     * @param id
     * @return Optional<Student> object
     */
    public Optional<Student> getStudentById(long id){
        return studentDataRepo.findById(id);
    }


    /**
     * Delete student witch this id
     * @param id
     */
    @Transactional
    public void deleteStudentById(long id){
        studentDataRepo.deleteById(id);
    }


    /**
     * Update all student data in database. Check if exist in database student with the same childName, childSurname and childBirthData (all 3).
     * If found  throw NotUniqDataException
     * @param id
     * @param student <- updated student data
     */
    @Transactional
    public void  updateStudent(long id,Student student){
        if(isStudentNotUniq(student)) throw new NotUniqDataException();

        student.setId(id);
        studentDataRepo.save(student);
    }


    /**
     * Update parentName in the student with this id
     * @param id
     * @param parentName <- new value of parentName
     */
    @Transactional
    public void updateParentName(long id,String parentName){
       studentDataRepo.updateStudentParentNameById(id, parentName);
    }

    @Transactional
    public void updateParentSurname(long id,String parentSurname){
        studentDataRepo.updateStudentParentSurnameById(id, parentSurname);
    }


    /**
     * Update childName in  student's  data with this id  in database. Check if exist in database student with the same childName, childSurname and childBirthData (all 3).
     * If found  throw NotUniqDataException
     * @param id
     * @param childName
     */
    @Transactional
    public void updateChildName(long id,String childName){
        Student student = studentDataRepo.findById(id).get();
        student.setChildName(childName);
        if(isStudentNotUniq(student)) throw new NotUniqDataException();
        studentDataRepo.updateStudentChildNameById(id, childName);
    }


    /**
     * Update child in  student's  data with this id  in database. Check if exist in database student with the same childName, childSurname and childBirthData (all 3).
     * If found  throw NotUniqDataException
     * @param id
     * @param childSurname
     */
    @Transactional
    public void updateChildSurname(long id,String childSurname){
        Student student = studentDataRepo.findById(id).get();
        student.setChildSurname(childSurname);
        if(isStudentNotUniq(student)) throw new NotUniqDataException();
        studentDataRepo.updateStudentChildSurnameById(id, childSurname);
    }

}
