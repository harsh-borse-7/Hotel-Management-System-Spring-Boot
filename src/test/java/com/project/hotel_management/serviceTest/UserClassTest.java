package com.project.hotel_management.serviceTest;

import com.project.hotel_management.exception.ResourceConflictException;
import com.project.hotel_management.exception.ResourceNotFoundException;
import com.project.hotel_management.model.User;
import com.project.hotel_management.repository.UserRepository;
import com.project.hotel_management.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserClassTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void testCreateUser_Success(){
        User mockUser = new User("harsh-borse", "password123", "user@gamil.com", "Admin");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User createdUser = userServiceImpl.createUser(mockUser);

        assertNotNull(createdUser);
        assertEquals("harsh-borse",createdUser.getUserName());
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void testCreateUser_UsernameAlreadyExists() {
        User user = new User("harsh-borse", "password123", "user@gamil.com", "Admin");

        when(userRepository.findByUserName("harsh-borse")).thenReturn(Optional.of(user));

        assertThrows(ResourceConflictException.class, () -> userServiceImpl.createUser(user));

        verify(userRepository, times(1)).findByUserName("harsh-borse");

        verify(userRepository, times(0)).save(any(User.class));
    }


    @Test
    void testUpdateUser_Success() {
        String userName = "harsh_borse";
        User existingUser = new User(userName, "password123", "harsh@gmail.com", "User");
        User updatedUserDetails = new User(userName, "newpassword", "harshborse@gmail.com", "Admin");
        User updatedUser = new User(userName, "newpassword", "harshborse@gmail.com", "Admin");

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userServiceImpl.updateUser(userName, updatedUserDetails);

        assertNotNull(result);
        assertEquals("harshborse@gmail.com", result.getEmail());
        assertEquals("newpassword", result.getPassword());
        assertEquals("Admin", result.getRole());

        verify(userRepository, times(1)).findByUserName(userName);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateUser_UserNotFound() {

        String userName = "harsh-borse";
        User updatedUserDetails = new User(userName, "newpassword", "harsh@example.com", "Admin");

        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.updateUser(userName, updatedUserDetails));

        verify(userRepository, times(1)).findByUserName(userName);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testGetAllUsers(){
        List<User> users = Arrays.asList(
                new User("harsh-borse", "password123", "harsh@gmail.com", "User"),
                new User("harsh", "newpassword", "harshborse@gmail.com", "Admin")
        );

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userServiceImpl.getAllUsers();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("harsh-borse", result.get(0).getUserName());
        assertEquals("harsh", result.get(1).getUserName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetAllUsers_EmptyList() {

        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> result = userServiceImpl.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userRepository, times(1)).findAll();
    }
    @Test
    void testFindByID_Success() {
        User user = new User("harsh-borse", "password123", "harsh@gmail.com", "User");
        user.setUserID(100L);

        when(userRepository.findById(100L)).thenReturn(Optional.of(user));

        // Call the service method
        User result = userServiceImpl.findByID(100L);
        assertNotNull(result);
        assertEquals(100L, result.getUserID());
        assertEquals("harsh-borse", result.getUserName());

        verify(userRepository, times(1)).findById(100L);
    }

    @Test
    void testFindByID_UserNotFound() {

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.findByID(999L));

        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    void testFindByUserName_Success() {
        User user = new User("harsh-borse", "password123", "harsh@gmail.com", "User");

        when(userRepository.findByUserName("harsh-borse")).thenReturn(Optional.of(user));

        // Call the service method
        User result = userServiceImpl.findByUserName("harsh-borse");
        assertNotNull(result);
        assertEquals("harsh-borse", result.getUserName());

        verify(userRepository, times(1)).findByUserName("harsh-borse");
    }

    @Test
    void testFindByUserName_UserNotFound() {
        when(userRepository.findByUserName("harsh")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.findByUserName("harsh"));

        verify(userRepository, times(1)).findByUserName("harsh");
    }

    @Test
    void testDeleteUser_Success() {
        User user = new User("harsh-borse", "password123", "harsh@gmail.com", "User");

        when(userRepository.findByUserName("harsh-borse")).thenReturn(Optional.of(user));

        userServiceImpl.deleteUser("harsh-borse");

        verify(userRepository, times(1)).delete(user);

        verify(userRepository, times(1)).findByUserName("harsh-borse");
    }
    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepository.findByUserName("non_existent_user")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.deleteUser("non_existent_user"));

        verify(userRepository, times(1)).findByUserName("non_existent_user");

        verify(userRepository, times(0)).delete(any(User.class));
    }

}
