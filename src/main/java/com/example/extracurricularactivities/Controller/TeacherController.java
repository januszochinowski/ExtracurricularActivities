package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.Service.JWTService;
import com.example.extracurricularactivities.Service.TeachersService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeachersService service;
    private final JWTService  jwtService;

    public TeacherController(TeachersService service, JWTService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<Teacher> getById(@RequestHeader("Authorization") String header){
        long id = Long.parseLong(jwtService.extractIdFromHeader(header));
        return ResponseEntity.ok(service.getTeacherById(id).orElseThrow(() -> new EntityNotFoundException("Cannot find teacher with id " + id)));
    }

    @DeleteMapping
    public ResponseEntity<Teacher> deleteById(@RequestHeader("Authorization") String header){
        long id = Long.parseLong(jwtService.extractIdFromHeader(header));
        service.deleteTeacherById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<String> updateTeacher(@RequestHeader("Authorization") String header,
                                                @RequestParam("part") StringBuilder partName,
                                                @RequestParam("value") String newValue) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        long id = Long.parseLong(jwtService.extractIdFromHeader(header));
        service.update(id,partName,newValue);

        return ResponseEntity.ok("Updated "+partName+"to value:" +newValue);
    }


}
