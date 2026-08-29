package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.Attendance;
import com.example.extracurricularactivities.Model.Lesson;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.Repo.LessonRepo;
import com.example.extracurricularactivities.config.JWTFilter;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.annotations.ColumnTransformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepo repo;
    private final TeachersService teachersService;
    private final ApplicationContext context;
    private final Logger logger = LoggerFactory.getLogger(LessonService.class);
    public LessonService(LessonRepo repo, TeachersService teachersService, ApplicationContext context) {
        this.repo = repo;
        this.teachersService = teachersService;
        this.context = context;
    }

    /**
     * Creat Lesson in every week between startDate and endDate in a database for new Activity
     * @param activity new Activity
     */
    @Transactional
    public void create(Activity activity){
        for(LocalDate date = activity.getStartDate(); date.isBefore(activity.getEndDate()); date = date.plusDays(7)){
            repo.save(new Lesson(activity,date));
        }
    }

    /**
     * Delete Lesson
     * @param id ID of Lesson to delete
     */
    public void delete(long id){
        repo.deleteById(id);
    }

    public Optional<Lesson> get(long id){
        return repo.findById(id);
    }

    /**
     * Get all Lesson
     * @param page page number
     * @param size page size
     * @return list of founded Lesson
     */
    public List<Lesson> getAll(int page, int size){
        return repo.findAll(PageRequest.of(page,size)).getContent();
    }

    /**
     *Get all Lesson of Activities belongs to selected Teacher
     * @param page number of pages
     * @param size  number of elements in a page
     * @param teacherId ID of selected teacher
     * @return list of founded Lesson
     */
    public List<Lesson> getAllMyTeacher(int page, int size, long teacherId){
        return repo.findByTeacherId(teacherId,PageRequest.of(page,size)).getContent();
    }

    /**
     *Get all Lesson with selected date of Activities belongs to selected Teacher
     * @param page number of pages
     * @param size  number of elements in a page
     * @param teacherId ID of selected teacher
     * @param date of a wanted lesson
     * @return list of founded Lesson
     */
    public List<Lesson> getAllMyTeacher(int page, int size, long teacherId, LocalDate date){
        return repo.findByTeacherIdInDay(teacherId,PageRequest.of(page,size),date).getContent();
    }

    public void update(Lesson lesson){
        repo.save(lesson);
    }

    /**
     * Mark selected Lesson as Cancel
     * @param id ID of selected Lesson
     */
    public void cancel(long id, boolean value){
        Lesson lesson = repo.findById(id).orElseThrow( () -> new EntityNotFoundException("Lesson with id " + id + " not found!") );
        lesson.setIsCancelled(value);
        update(lesson);
        logger.info("Lesson {} is cancelled", id);
    }

    /**
     * Update all Lesson data
     */
    public void updateDate(LocalDate newDate, long id) {
        Lesson lesson = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Lesson with id " + id + " not found!"));
        lesson.setDate(newDate);
        update(lesson);
    }

    /**
     * Update startTime in selected Lesson
     * @param newStartTime new StartTime
     * @param id ID of selected Lesson
     */
    public void updateStartTime(LocalTime newStartTime, long id) {
        Lesson lesson = repo.findById(id).orElseThrow( () -> new EntityNotFoundException("Lesson with id " + id + " not found!") );
        lesson.setStartTime(newStartTime);
        update(lesson);
    }

    /**
     *Mark in selected Lesson that the lesson is being taught by a different teacher
     * @param id ID of selected Lesson
     * @param teacherId ID of teacher who took them
     */
    public void takeSubstitute(long id, long teacherId){
        Lesson lesson = repo.findById(id).orElseThrow( () -> new EntityNotFoundException("Lesson with id " + id + " not found!") );
        Teacher teacher = teachersService.getTeacherById(teacherId).orElseThrow( () -> new EntityNotFoundException("Teacher not found!") );
        lesson.setSubstituteTeacher(teacher);
        update(lesson);
    }

    public List<Lesson> getStudentLessonsInDate(long studentId, LocalDate date ){
       return  context.getBean(AttendanceService.class).getByStudent(studentId).stream()
               .map((Attendance::getLesson))
               .filter(lesson -> lesson.getDate().isEqual(date))
               .toList();
    }











}
