package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Repo.UserDataRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MangeUserDataService {


    private  UserDataRepo userDataRepo;

    public MangeUserDataService(UserDataRepo userDataRepo) {
        this.userDataRepo = userDataRepo;
    }


    /**
     *  Saving new user in database
     * @param student
     * @return true if saved
     */
    public void createUser(Student student){

        if(IsUserDataUnique(student)) { throw new NotUniqDataException(); }
            userDataRepo.save(student);

    }

    /**
     * Check is exist another user with the same child name and child surname
     * @param student
     * @return true if that user isn't exist
     */
    public boolean IsUserDataUnique(Student student){
       return  userDataRepo.findSameUser(student.getChildName(), student.getChildSurname()) == 0;
    }


    public Optional<Student> getStudentById(long id){
        return userDataRepo.findById(id);
    }

    public boolean deleteStudentById(long id){
        userDataRepo.deleteById(id);
        return true;
    }

    public boolean updateStudent(Student student){
        userDataRepo.findSameUser(student.getChildName(), student.getChildSurname())
    }
}
