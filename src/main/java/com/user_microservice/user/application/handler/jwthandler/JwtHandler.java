package com.user_microservice.user.application.handler.jwthandler;

import com.user_microservice.user.application.dto.authenticationdto.AuthenticationRequest;
import com.user_microservice.user.application.dto.authenticationdto.AuthenticationResponse;
import com.user_microservice.user.domain.api.IAuthenticationServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtHandler implements IJwtHandler{

    private final IAuthenticationServicePort authenticationServicePort;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        String token = authenticationServicePort.login(request.getEmail(), request.getPassword());

        return new AuthenticationResponse(token);
    }
}
