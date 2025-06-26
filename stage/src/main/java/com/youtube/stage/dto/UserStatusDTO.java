package com.youtube.stage.dto;

import com.youtube.stage.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserStatusDTO {
    private Long id;
    private String email;
    private String username;
    private List<String> roles;
    private int followers;
    private String avatar;

    public UserStatusDTO(Long id, String email, String username, List<String> roles, int followers, String avatar) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.roles = roles;
        this.followers = followers;
        this.avatar = avatar;
    }

    public UserStatusDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = new ArrayList<>(user.getRoles());
        this.followers = user.getFollowers();
        this.avatar = user.getAvatarUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

