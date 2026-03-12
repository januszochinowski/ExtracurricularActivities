package com.example.extracurricularactivities.Model;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal  implements UserDetails {

    private final User user;

    public UserPrincipal(User user) { this.user = user; }

    @NotNull
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(user instanceof Student)
            return Collections.singleton(new SimpleGrantedAuthority("STUDENT"));
        else if(user instanceof Teacher)
            return ((Teacher) user).isAdmin ?
                    Collections.singleton(new SimpleGrantedAuthority("ADMIN"))
                    : Collections.singleton(new SimpleGrantedAuthority("TEACHER"));

        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getId().toString();
    }
}
