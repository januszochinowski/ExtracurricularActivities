package com.example.extracurricularactivities.Controller;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.Lesson;
import com.example.extracurricularactivities.Service.JWTService;
import com.example.extracurricularactivities.Service.LessonService;
import com.example.extracurricularactivities.Service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;
    private final JWTService jWTService;

    public LessonController(LessonService lessonService, JWTService jWTService) {
        this.lessonService = lessonService;
        this.jWTService = jWTService;
    }

    @GetMapping
    public ResponseEntity<Lesson> getLesson(@RequestParam long id) {
        return ResponseEntity.ok(lessonService.get(id).orElseThrow(()->new EntityNotFoundException("Lesson "+id + " not found")));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Lesson>> getLessons(@RequestParam long id,
                                                   @RequestParam int page,
                                                   @RequestParam int size) {
        return  ResponseEntity.ok(lessonService.getAll(page,size));
    }

    @GetMapping("/byTeacher")
    public ResponseEntity<List<Lesson>> getLessonByTeacher(@RequestParam int page,
                                                           @RequestParam int size,
                                                           @RequestParam(required = false) Optional<LocalDate> date,
                                                           @RequestHeader("Authorization") String header){
        long teacherId = Long.parseLong(jWTService.extractIdFromHeader(header));
        return date.map(localDate -> ResponseEntity.ok(lessonService.getAllMyTeacher(page, size, teacherId, localDate)))
                .orElseGet(() -> ResponseEntity.ok(lessonService.getAllMyTeacher(page, size, teacherId)));
    }

    /**
     * Lessons on date the user is enrolled
     * @param date
     * @return
     */
    @GetMapping("/byStudent")
    public ResponseEntity<List<Lesson>> getLessonByStudent(@RequestHeader("Authorization") String header,
                                                           @RequestParam LocalDate date) {
        long studentId = Long.parseLong(jWTService.extractIdFromHeader(header));
        return  ResponseEntity.ok(lessonService.getStudentLessonsInDate(studentId,date));
    }

    @PutMapping
    public ResponseEntity<String> updateLesson(@RequestBody Lesson lesson) {
        lessonService.update(lesson);
        return ResponseEntity.ok("Update lesson id "+lesson.getId());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLesson(@RequestParam long id) {
        lessonService.delete(id);
        return ResponseEntity.ok("Delete lesson id "+id);
    }

    @PatchMapping("/date")
    public ResponseEntity<String> updateLessonDate(@RequestParam long id,
                                               @RequestParam(name = "value") LocalDate newValue) {
       lessonService.updateDate(newValue,id);
       return ResponseEntity.ok("Update lesson id "+id);
    }

    @PatchMapping("/startTime")
    public ResponseEntity<String> updateLessonStartTime(@RequestParam long id,@RequestParam(name = "value") LocalTime newValue) {
        lessonService.updateStartTime(newValue,id);
        return ResponseEntity.ok("Update lesson start time "+id);
    }

    @PatchMapping("/substitute")
    public ResponseEntity<String> updateLessonSubstitute(@RequestParam long id,
                                                         @RequestParam (name="value") long  substituteTeacherId){
        lessonService.takeSubstitute(id,substituteTeacherId);
        return ResponseEntity.ok("Update lesson id "+id);
    }

    @PatchMapping("/cancel")
    public ResponseEntity<String> updateLessonCancel(@RequestParam long id,
                                                     @RequestParam( required = false) Optional<Boolean> value) {

        lessonService.cancel(id,value.orElse(true));
        return ResponseEntity.ok( value.isEmpty() ?  "Cancel lesson id "+id : "Uncanceled lesson" + id );
    }



}
