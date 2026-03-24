package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.*;
import com.example.extracurricularactivities.Repo.AttendanceRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttendanceService {

    private final ActivityService activityService;
    private final LessonService lessonService;
    private final StudentService studentService;

    private final AttendanceRepo repo;

    public AttendanceService(ActivityService activityService, LessonService lessonService, StudentService studentService, AttendanceRepo repo) {
        this.activityService = activityService;
        this.lessonService = lessonService;
        this.studentService = studentService;
        this.repo = repo;
    }

    public void create(Lesson lesson, Student student){
        Attendance attendance = new Attendance(student,lesson);
        repo.save(attendance);
    }

    public List<Attendance> getAll(Lesson lesson) {
        return lesson.getAttendances();
    }

    public Attendance getOne(AttendanceKey attendanceKey) {
        return null;
    }

    /**
     * Drop out student form Activity
     * @param studentId
     * @param activityId
     */
    public void studentDropOut(long studentId, long activityId) {
         Activity activity = activityService.getActivityById(activityId).orElseThrow(() -> new EntityNotFoundException("Activity with id " + activityId + " not found"));
         activity.getLesson().forEach( lesson -> {repo.deleteByStudentIdAndLessonId(studentId,lesson.getId()); });
    }

    /**
     * Mark student attendance in selected Lesson
     * @param studentId student ID
     * @param lessonId lesson ID
     * @param isAbsent set up student attendance (true = present, false = absent)
     */
    public void markStudentAbsent(long studentId, long lessonId, boolean isAbsent) {
        Attendance attendance = repo.findByStudentIdAndLessonId(studentId,lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Attendance with  student id " + studentId + "and lesson id" + lessonId +" not found"));
        attendance.setIsPresent(isAbsent);
        repo.save(attendance);
    }

    /**
     * Sing up selected student to Activity
     * @param activity selected Activity (object)
     * @param userId ID of a selected student
     */
    @Transactional
    public void signUpToActivity(Activity activity, long userId) {
        Student student = studentService.getStudentById(userId).orElseThrow(() -> new EntityNotFoundException("Student with id: " + userId + " not found"));
        activity.getLesson().parallelStream().forEach(lesson -> create(lesson,student));
    }

    @Transactional
    public void signUpToActivity(long activityID, long userId){
        Activity activity = activityService.getActivityById(activityID).orElseThrow(()->new EntityNotFoundException("Activity not found"));
        signUpToActivity(activity,userId);
    }
}
