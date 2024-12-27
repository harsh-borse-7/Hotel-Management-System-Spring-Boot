package com.project.hotel_management.service;

import com.project.hotel_management.model.User;

public interface AuthorizationService {

    boolean isAdmin(User user);

    boolean isUser(User user);

    boolean hasAccessToAdminFunctions(User user);

    boolean hasAccessToUserFunctions(User user);
}
