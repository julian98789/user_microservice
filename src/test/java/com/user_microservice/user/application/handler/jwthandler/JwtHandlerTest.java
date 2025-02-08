package com.user_microservice.user.application.handler.jwthandler;

import com.user_microservice.user.application.dto.authenticationdto.AuthenticationRequest;
import com.user_microservice.user.application.dto.authenticationdto.AuthenticationResponse;
import com.user_microservice.user.domain.api.IAuthenticationServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JwtHandlerTest {

    @Mock
    private IAuthenticationServicePort authenticationServicePort;

    @InjectMocks
    private JwtHandler jwtHandler;

    private  AuthenticationRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new AuthenticationRequest();
        request.setEmail("user@example.com");
        request.setPassword("password");
    }

    @Test
    @DisplayName("Given valid credentials, when login, then return AuthenticationResponse with token")
    void givenValidCredentials_whenLogin_thenReturnAuthenticationResponseWithToken() {
        String token = "jwtToken";
        AuthenticationResponse expectedResponse = new AuthenticationResponse(token);

        when(authenticationServicePort.login(request.getEmail(), request.getPassword())).thenReturn(token);

        AuthenticationResponse actualResponse = jwtHandler.login(request);

        assertEquals(expectedResponse, actualResponse);
        verify(authenticationServicePort, times(1))
                .login(request.getEmail(), request.getPassword());
    }
}