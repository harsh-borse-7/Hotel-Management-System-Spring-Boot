package com.project.hotel_management.controller;

import com.project.hotel_management.dto.LoginRequest;
import com.project.hotel_management.model.User;
import com.project.hotel_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel-management")
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();

        User user = userService.findByUserName(userName);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Check if password matches
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.badRequest().body("Invalid password");
        }

        return ResponseEntity.ok("Login successful");

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", "/hotel-management/users");
//        return ResponseEntity.status(302).headers(headers).build();
    }

}
