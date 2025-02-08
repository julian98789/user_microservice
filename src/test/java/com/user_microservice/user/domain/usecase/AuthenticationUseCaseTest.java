package com.user_microservice.user.domain.usecase;

import com.user_microservice.user.domain.exception.AuthenticationException;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.security.IAuthenticationSecurityPort;
import com.user_microservice.user.domain.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthenticationUseCaseTest {

    @Mock
    private IAuthenticationSecurityPort authenticationSecurityPort;

    @InjectMocks
    private AuthenticationUseCase authenticationUseCase;

    private String email;
    private String password;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
         email = "user@example.com";
         password = "password";
    }

    @Test
    @DisplayName("Given valid credentials, when login, then return JWT token")
    void givenValidCredentials_whenLogin_thenReturnJwtToken() {
        String token = "jwtToken";
        UserModel user = new UserModel();

        when(authenticationSecurityPort.validateCredentials(email, password)).thenReturn(true);
        when(authenticationSecurityPort.authenticate(email, password)).thenReturn(user);
        when(authenticationSecurityPort.generateToken(user)).thenReturn(token);

        String result = authenticationUseCase.login(email, password);

        assertEquals(token, result);

        verify(authenticationSecurityPort, times(1)).validateCredentials(email, password);
        verify(authenticationSecurityPort, times(1)).authenticate(email, password);
        verify(authenticationSecurityPort, times(1)).generateToken(user);
    }

    @Test
    @DisplayName("Given invalid credentials, when login, then throw AuthenticationException")
    void givenInvalidCredentials_whenLogin_thenThrowAuthenticationException() {

        when(authenticationSecurityPort.validateCredentials(email, password)).thenReturn(false);

        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            authenticationUseCase.login(email, password);
        });

        assertEquals(Util.INVALID_USER_CREDENTIALS, exception.getMessage());

        verify(authenticationSecurityPort, times(1)).validateCredentials(email, password);
    }
}