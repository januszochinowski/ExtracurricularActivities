package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.Repo.TeacherRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MangeUsersService {

    private TeacherRepo repo;

    public MangeUsersService(TeacherRepo repo) {
        this.repo = repo;
    }

    /**
     * Add new Teacher to database
     * @param teacher <- new teacher
     * @return id of new teacher
     */
    public Long addTeacher(Teacher teacher) {
        return repo.save(teacher).getId();
    }

    /**
     * Found teacher with given id
     * @param id <- teacher id
     * @return optional object of teacher
     */
    public Optional<Teacher> findTeacherById(Long id) {
        return repo.findById(id);
    }

    /**
     * Delete teacher with given id
     * @param id <- teacher id
     */
    public void deleteTeacherById(Long id) {
        repo.deleteById(id);
    }

    /**
     * Give one page  with Teacher objects
     * @param maxSize <- max amount on one page
     * @param pageNumber <- number of page
     * @return List of Teachers from chosen page
     */
    public List<Teacher> getAllTeachers(int maxSize, int pageNumber) {
        return repo.getAllTeachers(PageRequest.of(pageNumber, maxSize)).getContent();
    }

    /**
     * Give one page with Teachers with given name
     * @param name <- start letters or full name of the teacher you are looking for
     * @param maxSize <- max amount of page
     * @param pageNumber <- number of page
     * @return List of Teachers from chosen page
     */
    public List<Teacher>  getAllTeachersWithName(String name, int maxSize, int pageNumber) {
        return repo.findTeachersByNameStartingWith(name,PageRequest.of(pageNumber, maxSize)).getContent();
    }

    /**
     * Give one page with Teachers with given surname
     * @param name <- start letters or full name of the teacher you are looking for
     * @param maxSize <- max amount of page
     * @param pageNumber <- number of page
     * @return List of Teachers from chosen page
     */
    public List<Teacher> getAllTeachersWithSurname(String name, int maxSize, int pageNumber) {
        return repo.findTeachersBySurnameStartingWith(name ,PageRequest.of(pageNumber, maxSize)).getContent();
    }



}
