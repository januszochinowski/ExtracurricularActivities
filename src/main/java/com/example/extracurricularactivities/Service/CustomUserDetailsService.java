package com.example.extracurricularactivities.Service;

import com.example.extracurricularactivities.Model.User;
import com.example.extracurricularactivities.Model.UserPrincipal;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {



    private final MangeStudentDataService mangeStudentDataService;

    public CustomUserDetailsService(MangeStudentDataService mangeStudentDataService) {
        this.mangeStudentDataService = mangeStudentDataService;
    }


    @NotNull
    @Override
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {

        User user = mangeStudentDataService.getStudentById(Long.parseLong(username)).orElseThrow((() -> new UsernameNotFoundException(username)));

        return new UserPrincipal(user);
    }
}
