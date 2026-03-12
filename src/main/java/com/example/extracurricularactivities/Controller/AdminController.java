package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.Model.User;
import com.example.extracurricularactivities.Service.MangeStudentDataService;
import com.example.extracurricularactivities.Service.MangeTeachersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * User data operations for administrators only
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private MangeTeachersService teacherService;
    private MangeStudentDataService studentService;

    public AdminController(MangeTeachersService teacherService, MangeStudentDataService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    /**
     * Get all users with role
     * @param page page number
     * @param size max number of elements of page
     * @param role role of Users which being searching
     * @return List of found User and ok status
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(@RequestParam(required = false,defaultValue = "0") int page,
                                                 @RequestParam int size,
                                                 @RequestParam(required = false, defaultValue = "student") String role) {

        List<User> users = new ArrayList<>();

         role = role.toLowerCase();
        switch (role) {
            case "teacher" -> users.addAll(teacherService.getAllTeachers(size, page));
            case "admin" -> users.addAll(teacherService.getAllAdmin(size, page));
            case "student" -> {
            }
            // users.addAll()
        }
        return  new ResponseEntity<>(users, HttpStatus.OK);

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
                                                @PathVariable("part") String partName) throws NoSuchMethodException {
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
