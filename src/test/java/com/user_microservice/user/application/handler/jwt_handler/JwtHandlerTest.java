package com.user_microservice.user.application.handler.jwt_handler;

import com.user_microservice.user.application.dto.authentication_dto.AuthenticationRequest;
import com.user_microservice.user.application.dto.authentication_dto.AuthenticationResponse;
import com.user_microservice.user.domain.api.IAuthenticationServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class JwtHandlerTest {

    @Mock
    private IAuthenticationServicePort authenticationServicePort;

    @InjectMocks
    private JwtHandler jwtHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Login should return AuthenticationResponse with token")
    void login() {
        AuthenticationRequest request = new AuthenticationRequest("user@example.com", "password");
        String token = "jwtToken";
        AuthenticationResponse expectedResponse = new AuthenticationResponse(token);

        when(authenticationServicePort.login(request.getEmail(), request.getPassword())).thenReturn(token);

        AuthenticationResponse actualResponse = jwtHandler.login(request);

        assertEquals(expectedResponse, actualResponse);
    }
}