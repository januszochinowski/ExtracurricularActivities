package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Exception.NotUniqDataException;
import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Repo.StudentDataRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class StudentService {


    private StudentDataRepo repo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public StudentService(StudentDataRepo repo) {
        this.repo = repo;

    }


    /**
     *  Saving new student in database
     * @param student
     * @return id of created a student
     */
    @Transactional
    public Long createStudent(Student student){

        if(isStudentNotUniq(student))  throw new NotUniqDataException();
        student.setPassword(encoder.encode(student.getPassword()));
        return repo.save(student).getId();
    }


    /**
     * Check if there is  another student with the same details: childName, childSurname, childBirthDate (all 3)
     * @param childName <- childName to check
     * @param childSurname <- childSurname to check
     * @param childAge <- childBirthDate to check
     * @return true if a student with the same childName, childSurname and childBirthDate exist in database
     */
    public boolean isStudentNotUniq(String childName,String childSurname, int childAge){
       return  repo.findSameStudent(childName, childSurname,childAge) >  0;
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
        return repo.findById(id);
    }

    public List<Student> getAllStudents(int maxSize, int page){
        return repo.findAll(PageRequest.of(page,maxSize)).getContent();
    }


    public List<Student> getStudentsStartWith(String partName, String start, int maxSize, int page){

        switch(partName.toLowerCase()){
            case "childname" -> {return repo.findStudentByChildNameStartingWith(start, PageRequest.of(page,maxSize)).getContent();}
            case "childsurname" ->{return repo.findStudentByChildSurnameStartingWith(start, PageRequest.of(page,maxSize)).getContent();}
            case "parentsurname" ->{return repo.findStudentByParentSurnameStartingWith(start, PageRequest.of(page,maxSize)).getContent();}
            case "email" ->  {return repo.findStudentByEmailStartingWith(start, PageRequest.of(page,maxSize)).getContent();}
            default -> throw new EntityNotFoundException("Invalid part name");
        }
    }


    /**
     * Delete student witch this id
     * @param id
     */
    @Transactional
    public void deleteStudentById(Long id){
        repo.deleteById(id);
    }


    public void updateStudent(Long id, StringBuilder partName, String newValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.getClass().getDeclaredMethod("update" + ActivityService.firstLetterToUpper(partName), Long.class,String.class).invoke(this,id, newValue);
    }

    /**
     * Update all student data in the database. Check if exists in a database student with the same childName, childSurname and childBirthData (all 3).
     * If found  throw NotUniqDataException
     * @param id
     * @param student <- updated student data
     */
    @Transactional
    public void  updateStudent(Long id,Student student){
        if(isStudentNotUniq(student)) throw new NotUniqDataException();

        student.setId(id);
        repo.save(student);
    }


    /**
     * Update parentName in the student with this id
     * @param id
     * @param parentName <- new value of parentName
     */
    @Transactional
    public void updateParentName(Long id,String parentName){
       repo.updateStudentParentNameById(id, parentName);
    }

    /**
     * Update parentSurname in the student with this id
     * @param id
     * @param parentSurname <- new value of parentSurname
     */

    @Transactional
    public void updateParentSurname(Long id,String parentSurname){
        repo.updateStudentParentSurnameById(id, parentSurname);
    }


    /**
     * Update childName in  student's  data with this id  in database. Check if exist in database student with the same childName, childSurname and childBirthData (all 3).
     * If found  throw NotUniqDataException
     * @param id
     * @param childName
     */
    @Transactional
    public void updateChildName(Long id,String childName){
        Student student = repo.findById(id).orElseThrow(NullPointerException::new);
        if(isStudentNotUniq(childName,student.getChildSurname(),student.getChildAge())) throw new NotUniqDataException();
        repo.updateStudentChildNameById(id, childName);
    }


    /**
     * Update child in  student's  data with this id  in database. Check if exist in database student with the same childName, childSurname and childBirthData (all 3).
     * If found  throw NotUniqDataException
     * @param id
     * @param childSurname <- new value of childSurname
     */
    @Transactional
    public void updateChildSurname(Long id,String childSurname){
        Student student = repo.findById(id).orElseThrow(NullPointerException::new);
        if(isStudentNotUniq(student.getChildName(),childSurname,student.getChildAge())) throw new NotUniqDataException();
        repo.updateStudentChildSurnameById(id, childSurname);
    }

    @Transactional
    public void updateChildAge( Long id,String childAge){
        Student student = repo.findById(id).orElseThrow(NullPointerException::new);
        if(isStudentNotUniq(student.getChildName(),student.getChildSurname(),Integer.parseInt(childAge))) throw new NotUniqDataException();
        repo.updateStudentChildAge(id, Integer.parseInt(childAge));
    }






}
