package com.alten.ecommerce.utils;

import com.alten.ecommerce.entities.User;
import com.alten.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateUsernameAndEmail(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        // Validate username
        if (user.getUsername() == null || !StringUtils.hasText(user.getUsername())) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }

        // Validate email
        if (user.getEmail() == null || !StringUtils.hasText(user.getEmail())) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
    }

    // Check if the username or email already exists in the database
    public void validateUniqueFields(User user) {
        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }
    }
}
