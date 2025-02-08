package com.user_microservice.user.application.handler.userhandler;

import com.user_microservice.user.application.dto.userdto.UserRequest;
import com.user_microservice.user.application.dto.userdto.UserResponse;

public interface IUserHandler {
    UserResponse registerUser (UserRequest userRequest);
}
