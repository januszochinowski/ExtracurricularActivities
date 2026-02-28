package com.example.extracurricularactivities.Controller;


import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Service.MangeUserDataService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

/**
 * Controller for operation with student data
 */

@RestController
@RequestMapping("/studentData")
public class MangeUserDataController {

    private final  MangeUserDataService mangeUserDataService;

    public MangeUserDataController(MangeUserDataService mangeUserDataService) {
        this.mangeUserDataService = mangeUserDataService;
    }



    /**
     * Send student data
     * @param id <- student id
     * @return  ok status and student data if student with this id found
     */
    @GetMapping("")
    public ResponseEntity<Student> getStudentData(@RequestParam("id") long id){
        return ResponseEntity.ok(mangeUserDataService.getStudentById((long) id).orElseThrow(EntityNotFoundException::new));
    }

    /**
     * Update all student data
     * @param id <- id student to update
     * @param student <- new data
     * @return ok status if update completed successfully
     */
    @PutMapping("")
    public ResponseEntity<String> updateStudentData(@RequestParam("id") Long id,@RequestBody Student student){
        mangeUserDataService.updateStudent(id, student);
        return ResponseEntity.ok("Student updated");
    }

    /**
     * Update one of student attribute
     * @param part <- name of student attribute to update
     * @param id <- id student to update
     * @param newValue <- new value of updated attribute
     * @return ok status if update completed successfully
     */
    @PatchMapping("/{part}")
    public ResponseEntity<String> updateStudentData(@PathVariable("part") String part, @RequestParam("id") Long id,@RequestParam("value") String newValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "update" + part;
        mangeUserDataService.getClass().getDeclaredMethod(methodName,Long.class,String.class).invoke(mangeUserDataService,id,newValue);
        return ResponseEntity.ok("Student " + part + " updated");
    }


    /**
     * Delete all student data
     * @param id <- id student to delete
     * @return ok status if delete completed successfully
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudentData(@RequestParam("id") Long id){
        mangeUserDataService.deleteStudentById(id);
        return ResponseEntity.ok("Student deleted");
    }













    
}
