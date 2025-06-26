package com.youtube.stage.dto;

import java.util.Set;

public class LoginResponseDto {
    private String token;
    private long expiresIn;
    private String username;
    private String email;
    private Set<String> roles;

    // Getters e setters fluenti
    public LoginResponseDto setToken(String token) {
        this.token = token;
        return this;
    }

    public LoginResponseDto setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public LoginResponseDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public LoginResponseDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public LoginResponseDto setRoles(Set<String> roles) {
        this.roles = roles;
        return this;
    }

    // Getters normali se ti servono (opzionali)
    public String getToken() { return token; }
    public long getExpiresIn() { return expiresIn; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Set<String> getRoles() { return roles; }
}
