package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Exception.AccessForbiddenActivity;
import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Repo.ActivityRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepo repo;
    private final TeachersService teacherService;

    public ActivityService(ActivityRepo repo, TeachersService teacherService) {
        this.repo = repo;
        this.teacherService = teacherService;
    }

    /**
     * Add new Activity
     * @param activity activity to add
     * @param teacherId Teacher who adds activity
     */
    public void addActivity(Activity activity, Long teacherId){
        activity.setTeacher(teacherService.getTeacherById(teacherId).orElseThrow(()->new EntityNotFoundException("Teacher not found")));
        repo.save(activity);
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
