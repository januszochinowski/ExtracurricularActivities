package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Model.User;
import com.example.extracurricularactivities.Model.UserPrincipal;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {



    private final StudentService studentDataService;
    private final TeachersService teachersService;

    public CustomUserDetailsService(StudentService studentDataService, TeachersService teachersService) {
        this.studentDataService = studentDataService;
        this.teachersService = teachersService;
    }


    @NotNull
    @Override
    public UserDetails loadUserByUsername(@NotNull String id) throws UsernameNotFoundException {


        Optional<Student> student = studentDataService.getStudentById(Long.parseLong(id));
        User user = student.isPresent()?
                student.get()
                : teachersService.getTeacherById(Long.parseLong(id)).orElseThrow(() -> new UsernameNotFoundException(id));

        return  new UserPrincipal(user);
    }
}
