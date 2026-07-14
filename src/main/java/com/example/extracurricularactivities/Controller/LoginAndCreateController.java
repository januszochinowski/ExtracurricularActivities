package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Exception.GlobalExceptionHandler;
import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Model.User;
import com.example.extracurricularactivities.Model.UserPrincipal;
import com.example.extracurricularactivities.Service.JWTService;
import com.example.extracurricularactivities.Service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for login and register operation (only for student)
 */
@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("")
public class LoginAndCreateController {


    private final StudentService studentService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final Logger logger = LoggerFactory.getLogger(LoginAndCreateController.class);

    public LoginAndCreateController(StudentService studentService, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.studentService = studentService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Create record for a given student in database
     * @param student <- new student to create
     * @return ok status if created completed successfully
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createStudent(@RequestBody Student student){
        Long id = studentService.createStudent(student);
        logger.info(" User with id {} and with password {}created", id, student.getPassword());
        return  new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> userLogin(@RequestBody User user, HttpServletResponse response){

        Map<String,Object> userData = new HashMap<>();
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getId().toString(),user.getPassword())
        );
        if(authentication.isAuthenticated()){
            logger.info(" User with id {} succefully login",user.getId());
            userData.put("role", authentication.getAuthorities());
            userData.put("token",jwtService.generateToken(user.getId().toString()) );
            return ResponseEntity.ok(userData);
            }

        return  ResponseEntity.badRequest().build();
    }

}