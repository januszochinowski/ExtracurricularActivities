package com.example.extracurricularactivities.Controller;


import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Service.MangeStudentDataService;
import com.example.extracurricularactivities.config.JWTFilter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

/**
 * Controller for operation with student data
 */

@RestController
@RequestMapping("/studentData")
public class MangeStudentDataController {

    private final MangeStudentDataService mangeStudentDataService;

    public MangeStudentDataController(MangeStudentDataService mangeStudentDataService) {
        this.mangeStudentDataService = mangeStudentDataService;

    }



    /**
     * Send student data
     * @return  ok status and student data if student with this id found
     */
    @GetMapping("")
    public ResponseEntity<Student> getStudentData(){
        long id = Long.parseLong(JWTFilter.id);
        return ResponseEntity.ok(mangeStudentDataService.getStudentById((long) id).orElseThrow(EntityNotFoundException::new));
    }

    /**
     * Update all student data
     * @param student <- new data
     * @return ok status if update completed successfully
     */
    @PutMapping("")
    public ResponseEntity<String> updateStudentData(@RequestBody Student student){
        Long id = Long.parseLong(JWTFilter.id);
        mangeStudentDataService.updateStudent(id, student);
        return ResponseEntity.ok("Student updated");
    }

    /**
     * Update one of student attribute
     * @param part <- name of student attribute to update (start with big letter)
     * @param newValue <- new value of updated attribute
     * @return ok status if update completed successfully
     */
    @PatchMapping("/{part}")
    public ResponseEntity<String> updateStudentData(@PathVariable("part") String part,@RequestParam("value") String newValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "update" + part;
        Long id = Long.parseLong(JWTFilter.id);
        mangeStudentDataService.getClass().getDeclaredMethod(methodName,Long.class,String.class).invoke(mangeStudentDataService,id,newValue);
        return ResponseEntity.ok("Student " + part + " updated");
    }


    /**
     * Delete all student data
     * @return ok status if delete completed successfully
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudentData(){
        Long id = Long.parseLong(JWTFilter.id);
        mangeStudentDataService.deleteStudentById(id);
        return ResponseEntity.ok("Student deleted");
    }













    
}
