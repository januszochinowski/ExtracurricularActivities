package com.example.extracurricularactivities.Controller;


import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Service.MangeUserDataService;
import com.example.extracurricularactivities.Service.NotUniqDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/userData")
public class MangeUserDataController {

    private final  MangeUserDataService mangeUserDataService;

    public MangeUserDataController(MangeUserDataService mangeUserDataService) {
        this.mangeUserDataService = mangeUserDataService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Student student){
       try {
           mangeUserDataService.createUser(student);
           return ResponseEntity.ok("User created");
       }catch(NotUniqDataException e) {
           return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }




    
}
