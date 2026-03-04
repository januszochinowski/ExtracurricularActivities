package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Model.User;
import com.example.extracurricularactivities.Service.JWTService;
import com.example.extracurricularactivities.Service.MangeStudentDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for login and register operation (only for student)
 */

@RestController
@RequestMapping("")
public class LoginAndCreateController {


    private final MangeStudentDataService mangeStudentDataService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public LoginAndCreateController(MangeStudentDataService mangeStudentDataService, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.mangeStudentDataService = mangeStudentDataService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Create record  for given student in database
     * @param student <- new student to create
     * @return ok status if created completed successfully
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createStudent(@RequestBody Student student){
        Long id = mangeStudentDataService.createStudent(student);
        return  new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody User user){
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getId().toString(),user.getPassword())
        );

        if(authentication.isAuthenticated())
            return ResponseEntity.ok(jwtService.generateToken(user.getId().toString()));

        return  ResponseEntity.badRequest().build();
    }
}
