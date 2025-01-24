package com.alten.ecommerce.services;

import com.alten.ecommerce.dtos.responses.UserResponse;
import com.alten.ecommerce.dtos.requests.LoginRequest;
import com.alten.ecommerce.dtos.requests.UserRegisterRequest;

public interface UserService {
    UserResponse register(UserRegisterRequest userRegisterRequest);
    String authenticate(LoginRequest loginRequest);
}
