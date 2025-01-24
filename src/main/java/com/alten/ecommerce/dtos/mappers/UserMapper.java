package com.alten.ecommerce.dtos.mappers;

import com.alten.ecommerce.dtos.requests.UserRegisterRequest;
import com.alten.ecommerce.dtos.responses.UserResponse;
import com.alten.ecommerce.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserResponse userToUserResponse(User user);
    User userRegisterRequestToUser(UserRegisterRequest userRegisterRequest);
}
