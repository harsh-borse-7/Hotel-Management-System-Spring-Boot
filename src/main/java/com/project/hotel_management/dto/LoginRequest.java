package com.project.hotel_management.dto;

public class LoginRequest {
    private String userName;
    private String password;

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
