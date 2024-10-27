package com.example.myapplication.models.api;

import com.example.myapplication.models.User;

public class LoginResponse {
    private String token;
    private User user;

    // Getter and Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
