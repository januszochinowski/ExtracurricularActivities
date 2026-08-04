package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Service.ActivityService;
import com.example.extracurricularactivities.Service.JWTService;
import com.example.extracurricularactivities.config.JWTFilter;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService service;
    private final JWTService jwtService;

    public ActivityController(ActivityService service, JWTService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @GetMapping()
    public ResponseEntity<Activity> getActivityById(@RequestParam long id){
        return ResponseEntity.ok(service.getActivityById(id).orElseThrow(() -> new EntityNotFoundException("Activity with id " + id + " not found!")));
    }

    @GetMapping("/byTeacher")
    public ResponseEntity<List<Activity>> getActivitiesByTeacherId(@RequestParam(name="text") long teacherId ,
                                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                                   @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                                   @RequestParam(required = false)LocalDate afterDate){
        if(afterDate != null)
            return ResponseEntity.ok(service.getActivitiesByTeacherId(teacherId,page,pageSize, afterDate));
        return ResponseEntity.ok(service.getActivitiesByTeacherId(teacherId, page, pageSize));
    }




    @GetMapping("/byName")
    public ResponseEntity<List<Activity>> getActivitiesByName(@RequestParam(name="text") String name,
                                                              @RequestParam(required = false, defaultValue = "0") int page,
                                                              @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                              @RequestParam(required = false)LocalDate afterDate){
        if(afterDate != null)
            return ResponseEntity.ok(service.getActivitiesByNameStartingWith(name , page, pageSize, afterDate));
        return ResponseEntity.ok(service.getActivitiesByNameStartingWith(name , page, pageSize));
    }

    @GetMapping("/byLocation")
    public ResponseEntity<List<Activity>> getActivitiesByLocation(@RequestParam(name="text") String location,
                                                                  @RequestParam(required = false, defaultValue = "0") int page,
                                                                  @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                                  @RequestParam(required = false)LocalDate afterDate){
        if(afterDate != null)
            return ResponseEntity.ok(service.getActivitiesByLocationStartingWith(location, page, pageSize, afterDate));
        return ResponseEntity.ok(service.getActivitiesByLocationStartingWith(location, page, pageSize));
    }

    @GetMapping("/byDayOfWeek")
    public ResponseEntity<List<Activity>> getActivitiesByDayOfWeek(@RequestParam(name="text") String day,
                                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                                   @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                                   @RequestParam(required = false) Optional<LocalDate> afterDate){
        return ResponseEntity.ok(service.getActivitiesByDayOfWeek(day, page, pageSize,afterDate));
    }

    @PutMapping("/add")
    public ResponseEntity<String> add(@RequestBody Activity activity, @RequestHeader("Authorization") String header){
        long id = Long.parseLong(jwtService.extractIdFromHeader(header));
        service.addActivity(activity,id);
        return ResponseEntity.ok("Activity  has been added");
    }

    @PutMapping()
    public ResponseEntity<String> updateActivity(@RequestBody Activity activity, @RequestHeader("Authorization")  String header){
        service.updateAll(activity,Long.parseLong(jwtService.extractIdFromHeader(header)));
        return ResponseEntity.ok("Activity "+ activity.getId() + " updated-");
    }



    @PatchMapping()
    public ResponseEntity<String> update(@RequestParam(name= "part") StringBuilder partName,
                                           @RequestParam(name="value") String newValue,
                                           @RequestParam(name="id") long activityId,
                                           @RequestHeader("Authorization") String header) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        long senderId = Long.parseLong(jwtService.extractIdFromHeader(header));
        service.update(partName,newValue,activityId,senderId);
        return  ResponseEntity.ok(partName + "in activity: " + activityId + " updated to value: " + newValue);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteActivity(@RequestParam long id, @RequestHeader("Authorization")  String header){
         long senderId = Long.parseLong(jwtService.extractIdFromHeader(header));
         service.deleteActivityById(id,senderId);
         return  ResponseEntity.ok("activity with id " + id + " deleted");
    }


}
