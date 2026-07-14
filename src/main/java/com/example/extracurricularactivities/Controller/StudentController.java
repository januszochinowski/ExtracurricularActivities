package com.example.extracurricularactivities.Controller;


import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Service.JWTService;
import com.example.extracurricularactivities.Service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

/**
 * Controller for operation with student data
 */
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/studentData")
public class StudentController {

    private final StudentService studentService;
    private final JWTService jwtService;

    public StudentController(StudentService studentService, JWTService jwtService) {
        this.studentService = studentService;
        this.jwtService = jwtService;
    }



    /**
     * Send student data
     * @return  ok status and student data if a student with this id found
     */
    @GetMapping("")
    public ResponseEntity<Student> getStudentData(@RequestHeader("Authorization") String header){
        long id = Long.parseLong(jwtService.extractIdFromHeader(header));
        return ResponseEntity.ok(studentService.getStudentById((long) id).orElseThrow(EntityNotFoundException::new));
    }

    /**
     * Update all student data
     * @param student <- new data
     * @return ok status if update completed successfully
     */
    @PutMapping("")
    public ResponseEntity<String> updateStudentData(@RequestHeader("Authorization") String header,@RequestBody Student student){
        Long id = Long.parseLong(jwtService.extractIdFromHeader(header));
        studentService.updateStudent(id, student);
        return ResponseEntity.ok("Student updated");
    }

    /**
     * Update one of student attributes
     * @param part <- name of student attribute to update
     * @param newValue <- new value of updated attribute
     * @return ok status if update completed successfully
     */
    @PatchMapping("/{part}")
    public ResponseEntity<String> updateStudentData(@PathVariable StringBuilder part,
                                                    @RequestParam("value") String newValue,
                                                    @RequestHeader("Authorization") String header) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        part.replace(0,1, String.valueOf(part.charAt(0)).toUpperCase());
        String methodName = "update" + part;
        Long id = Long.parseLong(jwtService.extractIdFromHeader(header));
        studentService.getClass().getDeclaredMethod(methodName,Long.class,String.class).invoke(studentService,id,newValue);
        return ResponseEntity.ok("Student " + part + " updated");
    }


    /**
     * Delete all student data
     * @return ok status if delete completed successfully
     */
    @DeleteMapping()
    public ResponseEntity<String> deleteStudentData(@RequestHeader("Authorization") String header){
        Long id = Long.parseLong(jwtService.extractIdFromHeader(header));
        studentService.deleteStudentById(id);
        return ResponseEntity.ok("Student deleted");
    }













    
}
