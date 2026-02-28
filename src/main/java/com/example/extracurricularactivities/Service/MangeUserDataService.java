package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Exception.NotUniqDataException;
import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Repo.StudentDataRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional
@Service
public class MangeUserDataService {


    private StudentDataRepo studentDataRepo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public MangeUserDataService(StudentDataRepo studentDataRepo) {
        this.studentDataRepo = studentDataRepo;

    }


    /**
     *  Saving new student in database
     * @param student
     * @return id of created student
     */
    @Transactional
    public Long createUser(Student student){

        if(isStudentNotUniq(student))  throw new NotUniqDataException();
        student.setPassword(encoder.encode(student.getPassword()));
        return studentDataRepo.save(student).getId();
    }


    /**
     * Check if there is  another student with the same details: childName, childSurname, childBirthDate (all 3)
     * @param childName <- childName to check
     * @param childSurname <- childSurname to check
     * @param childAge <- childBirthDate to check
     * @return true if student with the same childName,childSurname and childBirthDate exist in database
     */
    public boolean isStudentNotUniq(String childName,String childSurname, int childAge){
       return  studentDataRepo.findSameStudent(childName, childSurname,childAge) >  0;
    }

    public boolean isStudentNotUniq(Student student){
        return isStudentNotUniq(student.getChildName(),student.getChildSurname(),student.getChildAge());
    }


    /**
     * Find student by id
     * @param id
     * @return Optional<Student> object
     */
    public Optional<Student> getStudentById(Long id){
        return studentDataRepo.findById(id);
    }


    /**
     * Delete student witch this id
     * @param id
     */
    @Transactional
    public void deleteStudentById(Long id){
        studentDataRepo.deleteById(id);
    }


    /**
     * Update all student data in database. Check if exist in database student with the same childName, childSurname and childBirthData (all 3).
     * If found  throw NotUniqDataException
     * @param id
     * @param student <- updated student data
     */
    @Transactional
    public void  updateStudent(Long id,Student student){
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
    public void updateParentName(Long id,String parentName){
       studentDataRepo.updateStudentParentNameById(id, parentName);
    }

    /**
     * Update parentSurname in the student with this id
     * @param id
     * @param parentSurname <- new value of parentSurname
     */

    @Transactional
    public void updateParentSurname(Long id,String parentSurname){
        studentDataRepo.updateStudentParentSurnameById(id, parentSurname);
    }


    /**
     * Update childName in  student's  data with this id  in database. Check if exist in database student with the same childName, childSurname and childBirthData (all 3).
     * If found  throw NotUniqDataException
     * @param id
     * @param childName
     */
    @Transactional
    public void updateChildName(Long id,String childName){
        Student student = studentDataRepo.findById(id).orElseThrow(NullPointerException::new);
        if(isStudentNotUniq(childName,student.getChildSurname(),student.getChildAge())) throw new NotUniqDataException();
        studentDataRepo.updateStudentChildNameById(id, childName);
    }


    /**
     * Update child in  student's  data with this id  in database. Check if exist in database student with the same childName, childSurname and childBirthData (all 3).
     * If found  throw NotUniqDataException
     * @param id
     * @param childSurname <- new value of childSurname
     */
    @Transactional
    public void updateChildSurname(Long id,String childSurname){
        Student student = studentDataRepo.findById(id).orElseThrow(NullPointerException::new);
        if(isStudentNotUniq(student.getChildName(),childSurname,student.getChildAge())) throw new NotUniqDataException();
        studentDataRepo.updateStudentChildSurnameById(id, childSurname);
    }

    @Transactional
    public void updateChildAge( Long id,String childAge){
        Student student = studentDataRepo.findById(id).orElseThrow(NullPointerException::new);
        if(isStudentNotUniq(student.getChildName(),student.getChildSurname(),Integer.parseInt(childAge))) throw new NotUniqDataException();
        studentDataRepo.updateStudentChildAge(id, Integer.parseInt(childAge));
    }



}
