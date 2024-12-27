package com.project.hotel_management.service;

import com.project.hotel_management.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(String userName, User user);
    List<User> getAllUsers();
    User findByID(long userID);
    User findByUserName(String userName);
    void deleteUser(String userName);
}
