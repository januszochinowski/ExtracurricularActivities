package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Activity;
import com.example.extracurricularactivities.Model.AttendanceList;
import com.example.extracurricularactivities.Repo.AttendanceListRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceListService {

    private final AttendanceListRepo repo;

    public AttendanceListService(AttendanceListRepo repo) {
        this.repo = repo;
    }

    /**
     * Create new AttendanceList in a database
     * @param activity new Activity
     */
    public void create(Activity activity){
        for(LocalDate date = activity.getStartDate(); date.isBefore(activity.getEndDate()); date.plusDays(7)){
            repo.save(new AttendanceList(activity,date));
        }
    }

    /**
     * Delete Activity
     * @param id ID of Activity to delete
     */
    public void delete(long id){
        repo.deleteById(id);
    }

    public Optional<AttendanceList> get(long id){
        return repo.findById(id);
    }

    /**
     * Get all Activity
     * @param page page number
     * @param size page size
     * @return list of founded AttendanceList
     */
    public List<AttendanceList> getAll(int page, int size){
        return repo.findAll(PageRequest.of(page,size)).getContent();
    }

    /**
     *Get all AttendanceList of Activities belongs to selected Teacher
     * @param page number of pages
     * @param size  number of elemnts in a page
     * @param teacherId ID of selected teacher
     * @return list of founded AttendanceList
     */
    public List<AttendanceList> getAllMy(int page, int size, long teacherId){
        return repo.findByTeacherId(teacherId,PageRequest.of(page,size)).getContent();
    }

    /**
     *Get all AttendanceList with selected date of Activities belongs to selected Teacher
     * @param page number of pages
     * @param size  number of elements in a page
     * @param teacherId ID of selected teacher
     * @return list of founded AttendanceList
     */
    public List<AttendanceList> getAllMy(int page, int size, long teacherId,LocalDate date){
        return repo.findByTeacherIdInDay(teacherId,PageRequest.of(page,size),date).getContent();
    }

    public void update(AttendanceList attendanceList){
        repo.save(attendanceList);
    }

    public void update(StringBuilder partName, String newValue, long id) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AttendanceList attendanceList = repo.findById(id).orElseThrow( () -> new EntityNotFoundException("AttendanceList with id " + id + " not found!") );
        attendanceList.getClass().getDeclaredMethod("set"+ActivityService.firstLetterToUpper(partName),String.class).invoke(attendanceList,newValue);
        repo.save(attendanceList);
    }








}
