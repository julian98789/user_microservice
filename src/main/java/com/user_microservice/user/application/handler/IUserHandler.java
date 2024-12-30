package com.user_microservice.user.application.handler;

import com.user_microservice.user.application.dto.user_dto.UserRequest;
import com.user_microservice.user.application.dto.user_dto.UserResponse;

public interface IUserHandler {
    UserResponse registerUser (UserRequest userRequest);
}
