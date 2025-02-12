package com.user_microservice.user.application.handler.jwthandler;

import com.user_microservice.user.application.dto.authenticationdto.AuthenticationRequest;
import com.user_microservice.user.application.dto.authenticationdto.AuthenticationResponse;


public interface IJwtHandler {
    AuthenticationResponse login (AuthenticationRequest request);
}
