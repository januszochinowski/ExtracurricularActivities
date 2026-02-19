package com.example.extracurricularactivities.Controller;


import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Service.MangeUserDataService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;


@RestController
@RequestMapping("/userData")
public class MangeUserDataController {

    private final  MangeUserDataService mangeUserDataService;

    public MangeUserDataController(MangeUserDataService mangeUserDataService) {
        this.mangeUserDataService = mangeUserDataService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Student student){
           mangeUserDataService.createUser(student);
           return ResponseEntity.ok("Student created");
    }

    @GetMapping("")
    public ResponseEntity<Student> getStudentData(@RequestParam("id") Long id){
        return ResponseEntity.ok(mangeUserDataService.getStudentById(id).orElseThrow(EntityNotFoundException::new));
    }

    @PutMapping("")
    public ResponseEntity<String> updateStudentData(@RequestParam("id") Long id,@RequestBody Student student){
        mangeUserDataService.updateStudent(id, student);
        return ResponseEntity.ok("Student updated");
    }

    @PatchMapping("/{part}")
    public ResponseEntity<String> updateStudentData(@PathVariable("part") String part, @RequestParam("id") Long id,@RequestParam("value") String newValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "update" + part;
        mangeUserDataService.getClass().getDeclaredMethod(methodName,Long.class,String.class).invoke(mangeUserDataService,id,newValue);
        return ResponseEntity.ok("Student " + part + " updated");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudentData(@RequestParam("id") Long id){
        mangeUserDataService.deleteStudentById(id);
        return ResponseEntity.ok("Student deleted");
    }













    
}
