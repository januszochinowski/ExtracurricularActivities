package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Model.User;
import com.example.extracurricularactivities.Model.UserPrincipal;
import com.example.extracurricularactivities.Repo.StudentDataRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {



    private final MangeUserDataService mangeUserDataService;

    public CustomUserDetailsService(MangeUserDataService mangeUserDataService) {
        this.mangeUserDataService = mangeUserDataService;
    }


    @NotNull
    @Override
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {

        User user = mangeUserDataService.getStudentById(Long.parseLong(username)).orElseThrow((() -> new UsernameNotFoundException(username)));

        return new UserPrincipal(user);
    }
}
