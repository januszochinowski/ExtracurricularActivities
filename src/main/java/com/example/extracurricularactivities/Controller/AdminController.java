package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.Model.User;
import com.example.extracurricularactivities.Service.StudentService;
import com.example.extracurricularactivities.Service.TeachersService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * User data operations for administrators only
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private TeachersService teacherService;
    private StudentService studentService;

    public AdminController(TeachersService teacherService, StudentService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    /**
     * Get all users with role
     * @param page page number
     * @param size max number of elements of page
     * @param role role of Users which being searching
     * @param startWith give only this user whose selected field begins with this letter/s
     * @param partName name of selected field
     * @return List of found User and ok status
     */
    @GetMapping
    public ResponseEntity<List<? extends  User>> getAllUser(@RequestParam(required = false,defaultValue = "0") int page,
                                                 @RequestParam int size,
                                                 @RequestParam(required = false,value="role", defaultValue = "student") String role,
                                                 @RequestParam(required = false, value="start", defaultValue = "") String startWith,
                                                 @RequestParam(required = false, value="part") String partName) {

        if(startWith.isEmpty()) {

            switch (role.toLowerCase()) {
                case "teacher" -> {return ResponseEntity.ok(teacherService.getAllTeachers(size,page));}
                case "admin" -> {return ResponseEntity.ok(teacherService.getAllAdmin(size, page));}
                case "student" -> {return ResponseEntity.ok(studentService.getAllStudents(size, page));}
                default -> {throw new NoSuchElementException("Invalid role");}
            }

        }else {
            if (role.equals("student")) {
                return ResponseEntity.ok(studentService.getStudentsStartWith(partName, startWith, size, page));
            } else if (role.equals("teacher") || role.equals("admin") ) {
                return ResponseEntity.ok(teacherService.getTeacherStartWith(partName, startWith, size, page));
            } else
                throw new NoSuchElementException("Invalid  partName");
        }
    }


    /**
     * Get user with a selected role and ID
     * @param id id of wanted user
     * @param role role of wanted user
     * @return if found, ok status and User
     */
    @GetMapping("/{role}")
    public ResponseEntity<User> getUserById(@RequestParam long id, @PathVariable String role) {

        if(role.equalsIgnoreCase("student"))
            return ResponseEntity.ok(studentService.getStudentById(id).orElseThrow(EntityNotFoundException::new));
        else if (role.equalsIgnoreCase("teacher"))
            return ResponseEntity.ok(teacherService.getTeacherById(id).orElseThrow(EntityNotFoundException::new));
        else
            throw new EntityNotFoundException("Invalid name of part");


    }


    /**
     * Add new teacher
     * @param teacher  new teacher
     * @param role role of new teacher (teacher or admin)
     * @return  if teacher created ok status and id of new teacher
     */
    @PostMapping("")
    public ResponseEntity<Long> addTeacher(@RequestBody Teacher teacher, @RequestParam(required = false,defaultValue = "teacher") String role) {
        teacher.setIsAdmin(role.equals("admin"));
      return  new ResponseEntity<>(teacherService.addTeacher(teacher),HttpStatus.CREATED);
    }

    /**
     * Deleted selected teacher (admin also is teacher)
     * @param id id of teacher to delete
     * @return if deleted ok status
     */
    @DeleteMapping("/teacher")
    public ResponseEntity<String> deleteTeacher(@RequestParam long id) {
        teacherService.deleteTeacherById(id);
        return new ResponseEntity<>("Teacher successfully deleted ", HttpStatus.OK);
    }

    /**
     * Deleted selected student
     * @param id id of a student to delete
     * @return if deleted ok status
     */
    @DeleteMapping("/student")
    public ResponseEntity<String> deleteStudent(@RequestParam long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok("Student successfully deleted ");
    }

    /**
     * Update a User witch selected role
     * @param id User id
     * @param newValue new value of updated part
     * @param role role of User which updates part
     * @param partName name of part to update
     * @return if updated ok status
     * @throws NoSuchMethodException role name is invalid
     */
    @PatchMapping("/{role}/{part}")
    public ResponseEntity<String> updateTeacher(@RequestParam long id,
                                                @RequestParam("value") String newValue,
                                                @PathVariable("role")  String role,
                                                @PathVariable("part") StringBuilder partName) throws NoSuchMethodException {
        try {

            if(role.equals("teacher") || role.equals("admin")) {
                teacherService.update(id, partName, newValue);
            }else if(role.equals("student")) {
                studentService.updateStudent(id, partName, newValue);
            }else
                return ResponseEntity.badRequest().body("Invalid role");

        }catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
            throw new NoSuchMethodException(partName + "is wrong part name");
        }

        return new ResponseEntity<>("Teacher successfully updated ", HttpStatus.OK);
    }
}
