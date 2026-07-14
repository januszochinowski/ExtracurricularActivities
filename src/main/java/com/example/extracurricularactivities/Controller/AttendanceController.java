package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Attendance;
import com.example.extracurricularactivities.Service.AttendanceService;
import com.example.extracurricularactivities.Service.JWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService service;
    private final JWTService  jwtService;

    public AttendanceController(AttendanceService service, JWTService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<List<Attendance>> getAll(@RequestParam long lessonId){
        return ResponseEntity.ok(service.getAll(lessonId));
    }


    @DeleteMapping()
    public ResponseEntity<String> dropOutByStudent(@RequestParam("activity") Long activityId,@RequestHeader("Authorization")  String header){
        service.studentDropOut(Long.parseLong(jwtService.extractIdFromHeader(header)),activityId);
        return ResponseEntity.ok("Drop out student from activity " +  activityId);
    }

    @DeleteMapping("/teacher")
    public ResponseEntity<String> dropOutByTeacher(@RequestParam("activity") Long activityId, @RequestParam("student")  Long studentId){
        service.studentDropOut(studentId,activityId);
        return ResponseEntity.ok("Drop out student from activity " +  activityId);
    }

    @PatchMapping()
    public ResponseEntity<String> markStudent(@RequestParam long studentId,
                                              @RequestParam boolean present,
                                              @RequestParam long lessonId){
        service.markStudentAbsent(studentId,lessonId,present);
        return ResponseEntity.ok("Mark student" + (present?  "present" : "absent") +  "from activity " +  studentId);
    }

    @PostMapping()
    public ResponseEntity<String> sinUp(@RequestParam("activity") Long activityId, @RequestHeader("Authorization")  String header){
        service.signUpToActivity(activityId,Long.parseLong(jwtService.extractIdFromHeader(header)));
        return ResponseEntity.ok("Sign up to activity " +  activityId);
    }

    @PostMapping("/byTeacher")
    public ResponseEntity<String> singUpByTeacher(@RequestParam("activity") Long activityId,@RequestParam("student") Long studentId){
        service.signUpToActivity(activityId,studentId);
        return ResponseEntity.ok("Sing up student with id " + studentId + "to activity " + activityId);
    }





}
