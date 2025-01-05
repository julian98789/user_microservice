package com.user_microservice.user.application.handler.jwt_handler;

import com.user_microservice.user.application.dto.authentication_dto.AuthenticationRequest;
import com.user_microservice.user.application.dto.authentication_dto.AuthenticationResponse;


public interface IJwtHandler {
    AuthenticationResponse login (AuthenticationRequest request);
}
