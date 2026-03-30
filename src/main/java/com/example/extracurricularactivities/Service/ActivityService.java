package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Exception.AccessForbiddenActivity;
import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.Repo.ActivityRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepo repo;
    private final TeachersService teacherService;
    private final LessonService lessonSabsentervice;

    public ActivityService(ActivityRepo repo, TeachersService teacherService, LessonService lessonSabsentervice) {
        this.repo = repo;
        this.teacherService = teacherService;
        this.lessonSabsentervice = lessonSabsentervice;
    }

    /**
     * Add new Activity
     * @param activity activity to add
     * @param teacherId Teacher who adds activity
     */
    @Transactional
    public void addActivity(Activity activity, Long teacherId){
        activity.setTeacher(teacherService.getTeacherById(teacherId).orElseThrow(()->new EntityNotFoundException("Teacher not found")));
        activity.setStartTime(LocalTime.parse(activity.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
        repo.save(activity);
        lessonSabsentervice.create(activity);
    }


    /**
     * Get Activity by ID
     * @param id Activity ID
     * @return optional object with Activity
     */
    public Optional<Activity> getActivityById(Long id){
        return repo.findById(id);
    }

    /**
     * Get Activities by Teacher ID
     * @param teacherId ID of Teacher in wanted Activities
     * @param page number of wanted pages
     * @param pageSize number of elements in page
     * @return List of founded Activities
     */
    public List<Activity> getActivitiesByTeacherId(Long teacherId, int page, int pageSize){
        return repo.findActivitiesByTeacherId(teacherId, PageRequest.of(page, pageSize)).getContent();
    }

    public List<Activity> getActivitiesByTeacherId(Long teacherId, int page, int pageSize, LocalDate afterDate){
        return repo.findActivitiesByTeacherId(teacherId, PageRequest.of(page, pageSize)).getContent();
    }

    /**
     * Get Activities which name start with
     * @param name first letter/s of name
     * @param page number of wanted pages
     * @param pageSize number of elements in page
     * @return List of founded Activities
     */
    public List<Activity> getActivitiesByNameStartingWith(String name, int page, int pageSize){
        return repo.findActivitiesByNameStartingWith(name , PageRequest.of(page, pageSize)).getContent();
    }

    public List<Activity> getActivitiesByNameStartingWith(String name, int page, int pageSize, LocalDate afterDate){
        return repo.findActivitiesByNameStartingWith(name , PageRequest.of(page, pageSize),afterDate).getContent();
    }



    /**
     * Get Activities which Location name starts with
     * @param name first letter/s of location name
     * @param page number of wanted pages
     * @param pageSize number of elements in page
     * @return List of founded Activities
     */
    public List<Activity> getActivitiesByLocationStartingWith(String name, int page, int pageSize){
        return repo.findActivitiesByLocationStartingWith(name , PageRequest.of(page, pageSize)).getContent();
    }

    public List<Activity> getActivitiesByLocationStartingWith(String name, int page, int pageSize, LocalDate afterDate){
        return repo.findActivitiesByLocationStartingWith(name , PageRequest.of(page, pageSize),afterDate).getContent();
    }



    /**
     * Get Activities by day of week when they take place. It is assumed that Activity takes place only once a week on the same day.
     * @param dayOfWeek
     * @param page
     * @param pageSize
     * @return
     */
    public List<Activity> getActivitiesByDayOfWeek(String dayOfWeek, int page, int pageSize, Optional<LocalDate> afterDate){
        List<Activity> activities =  new ArrayList<>();
        Pageable pageable = PageRequest.of(page, pageSize);
        int pageCounter = 0;

        while(pageable.isPaged() && pageCounter < pageSize) {
            List<Activity> list = afterDate.isEmpty() ?
                                                        repo.findAll(pageable).getContent()
                                                        : repo.findAllAfterDate(pageable,afterDate.get()).getContent();

           list =  list.stream()
                    .filter(activity -> activity.getStartDate().getDayOfWeek().toString().equals(dayOfWeek.toUpperCase()))
                   .toList();

            pageCounter += list.size();
            if(list.isEmpty())
                break;
            activities.addAll(list);
            pageable = pageable.next();
        }


        return activities;
    }

    public List<Activity> getActivitiesByDayOfWeek(String dayOfWeek, int page, int pageSize){
        return getActivitiesByDayOfWeek(dayOfWeek, page, pageSize, Optional.empty());
    }




    /**
     * Update all data in selected activity
     * @param activity new data
     * @param senderId id of Teacher who send request
     */
    public void updateAll(Activity activity, long senderId){

        isActivityBelongNotToTeacher(activity,senderId);
        repo.save(activity);
    }

    /**
     * Delete selected Activity
     * @param id Activity ID
     * @param senderId ID of Teacher who send request
     */
    public void deleteActivityById(Long id, long senderId){

        isActivityBelongNotToTeacher(id, senderId);
        repo.deleteById(id);
    }

    /**
     * Update selected field in selected Activity
     * @param partName name of field to update
     * @param newValue new value of updated filed
     * @param activityId ID of the activity to be updated
     * @param senderId ID of Teacher who send the request
     */
    public void update(StringBuilder partName, String newValue,long activityId, long senderId) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Activity activity  = repo.findById(activityId).orElseThrow( () -> new EntityNotFoundException("Activity with id " + activityId + " not found!") );
        isActivityBelongNotToTeacher(activity,senderId);
        activity.getClass().getDeclaredMethod("set" +  firstLetterToUpper(partName),String.class).invoke(activity,newValue);
        repo.save(activity);
    }

    /**
     * Check if this teacher owns this Activity
     * @param activity Activity to check
     * @param teacherId Teacher ID
     */
    private void isActivityBelongNotToTeacher(Activity activity, long teacherId){
         isActivityBelongNotToTeacher(activity.getId(), teacherId);
    }

    private void isActivityBelongNotToTeacher(long activityId, long teacherId){
       if(repo.findTeacherId(activityId) !=  teacherId)
           throw new AccessForbiddenActivity(activityId);
    }

    /**
     * Make the first letter in String upper case
     * @param builder String to modify
     */
    public static StringBuilder firstLetterToUpper(StringBuilder builder){
       return builder.replace(0,1, String.valueOf(builder.charAt(0)).toUpperCase());
    }





}
