package com.user_microservice.user.infrastructure.input.controller;

import com.user_microservice.user.application.dto.authentication_dto.AuthenticationRequest;
import com.user_microservice.user.application.dto.authentication_dto.AuthenticationResponse;
import com.user_microservice.user.domain.api.IAuthenticationServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final IAuthenticationServicePort authenticationServicePort;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        logger.info("[Infraestructura] [AuthController] Iniciando proceso de autenticación.");
        String token = authenticationServicePort.login(request.getEmail(), request.getPassword());
        logger.info("[Infraestructura] [AuthController] Usuario autenticado correctamente.");
        AuthenticationResponse response = new AuthenticationResponse(token);
        return ResponseEntity.ok(response);
    }
}
