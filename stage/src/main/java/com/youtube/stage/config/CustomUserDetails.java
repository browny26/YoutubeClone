package com.youtube.stage.config;

import com.youtube.stage.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;


public class CustomUserDetails implements UserDetails {
    private final User user; // tua entity o modello utente

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getDisplayUsername() {
        return user.getUsername();
    }

    public String getEmail() {
        return user.getEmail(); // aggiungi questo metodo!
    }

    public Long getId() {
        return user.getId();
    }

    public Integer getFollower() {
        return user.getFollowers();
    }

    public String getAvatarUrl() {
        return user.getAvatarUrl();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }
}

