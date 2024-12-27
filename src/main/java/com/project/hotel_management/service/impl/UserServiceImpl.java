package com.project.hotel_management.service.impl;

import com.project.hotel_management.exception.ResourceConflictException;
import com.project.hotel_management.exception.ResourceNotFoundException;
import com.project.hotel_management.model.User;
import com.project.hotel_management.repository.UserRepository;
import com.project.hotel_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        Optional <User> findUser = userRepository.findByUserName(user.getUserName());
        if (findUser.isPresent()) {
            throw new ResourceConflictException("Username " + user.getUserName() + " already exists");
        }
        return userRepository.save(user);
    }
    @Override
    public User updateUser(String userName, User userDetails) {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        System.out.println(userOptional);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUserName(userDetails.getUserName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            return userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("User with ID " + userName + " not found");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByID(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }
    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + userName + " not found"));
    }
    @Override
    public void deleteUser(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with Name: " + userName));
        userRepository.delete(user);
    }

}
