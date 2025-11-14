package com.rems.service;

import com.rems.model.User;
import com.rems.model.enums.UserRole;
import com.rems.repository.UserRepository;
import com.rems.util.PasswordUtil;
import com.rems.util.SessionManager;

public class AuthService {

    private final UserRepository userRepository = new UserRepository();

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("No account found with that email.");
        }
        if (!PasswordUtil.verify(password, user.getPasswordHash())) {
            throw new RuntimeException("Incorrect password.");
        }
        SessionManager.login(user);
        return user;
    }

    public User register(String fullName, String email, String password, UserRole role) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("An account with this email already exists.");
        }
        User user = new User(fullName, email, PasswordUtil.hash(password), role);
        userRepository.save(user);
        return user;
    }

    public void logout() {
        SessionManager.logout();
    }
}
