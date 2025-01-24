package com.alten.ecommerce.services.impl;

import com.alten.ecommerce.dtos.mappers.UserMapper;
import com.alten.ecommerce.dtos.responses.UserResponse;
import com.alten.ecommerce.dtos.requests.LoginRequest;
import com.alten.ecommerce.dtos.requests.UserRegisterRequest;
import com.alten.ecommerce.entities.User;
import com.alten.ecommerce.repositories.UserRepository;
import com.alten.ecommerce.services.UserService;
import com.alten.ecommerce.utils.JwtUtil;
import com.alten.ecommerce.utils.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserValidator userValidator;


    @Override
    public UserResponse register(UserRegisterRequest userRegisterRequest) {

        User user = UserMapper.INSTANCE.userRegisterRequestToUser(userRegisterRequest);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userValidator.validateUsernameAndEmail(user);

        userValidator.validateUniqueFields(user);

        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @Override
    public String authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        return jwtUtil.generateToken(user.getEmail());
    }
}
