package com.project.hotel_management.service.impl;

import com.project.hotel_management.model.User;
import com.project.hotel_management.service.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Override
    public boolean isAdmin(User user) {
        return "admin".equals(user.getRole());
    }

    @Override
    public boolean isUser(User user) {
        return "user".equals(user.getRole());
    }

    @Override
    public boolean hasAccessToAdminFunctions(User user) {
        return isAdmin(user);
    }

    @Override
    public boolean hasAccessToUserFunctions(User user) {
        return isAdmin(user) || isUser(user);
    }
}
