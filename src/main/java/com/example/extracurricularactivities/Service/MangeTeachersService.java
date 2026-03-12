package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.Repo.TeacherRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class MangeTeachersService {

    private final TeacherRepo repo;
    private final ApplicationContext context;

    public MangeTeachersService(TeacherRepo repo,ApplicationContext context) {
        this.repo = repo;
        this.context = context;
    }

    /**
     * Add new Teacher to the database
     * @param teacher <- new teacher
     * @return id of new teacher
     */
    public Long addTeacher(Teacher teacher) {
        teacher.setPassword(context.getBean(BCryptPasswordEncoder.class,"passwordEncoder").encode(teacher.getPassword()));
        return repo.save(teacher).getId();
    }

    /**
     * Found teacher with given id
     * @param id <- teacher id
     * @return optional object of teacher
     */
    public Optional<Teacher> getTeacherById(Long id) {
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
        return repo.getAllTeachers(PageRequest.of(pageNumber, maxSize, Sort.by("surname"))).getContent();
    }

    public List<Teacher> getAllAdmin(int maxSize, int pageNumber) {
        return repo.findAllAdmin(PageRequest.of(pageNumber, maxSize, Sort.by("surname"))).getContent();
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

    /**
     * Update filed in Teacher with given partName
     * @param id <- teacher's id
     * @param partName <- name filed to update
     * @param newValue <- new value
     */
   public void  update(Long id,String partName, String newValue ) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Teacher teacher = repo.findById(id).orElseThrow( () -> new EntityNotFoundException("Teacher with id " + id + " not found!") );
        teacher.getClass().getDeclaredMethod("set" + partName,String.class).invoke(teacher,newValue);
        repo.save(teacher);
   }








}
