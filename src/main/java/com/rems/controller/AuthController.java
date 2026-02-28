package com.rems.controller;

import com.rems.model.User;
import com.rems.model.enums.UserRole;
import com.rems.service.AuthService;

public class AuthController {

    private final AuthService authService = new AuthService();

    public User login(String email, String password) {
        return authService.login(email, password);
    }

    public User register(String fullName, String email,
                          String password, UserRole role) {
        return authService.register(fullName, email, password, role);
    }

    public void logout() {
        authService.logout();
    }
}
