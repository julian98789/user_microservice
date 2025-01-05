package com.user_microservice.user.domain.usecase;

import com.user_microservice.user.domain.exception.AuthenticationException;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.security.IAuthenticationSecurityPort;
import com.user_microservice.user.domain.util.RoleName;
import com.user_microservice.user.domain.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AuthenticationUseCaseTest {

    @Mock
    private IAuthenticationSecurityPort authenticationSecurityPort;

    @InjectMocks
    private AuthenticationUseCase authenticationUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("El inicio de sesion debe retornar un token cuando las credenciales son validas")
    void loginCase1() {
        String email = "user@example.com";
        String password = "password";
        String token = "jwtToken";
        UserModel user = new UserModel(1L, new RoleModel(1L, RoleName.ADMIN, "admin"),
                "password", "test@example.com", LocalDate.of(2000, 1, 1),
                "123456789", "ID123", "Doe", "John");

        when(authenticationSecurityPort.validateCredentials(email, password)).thenReturn(true);
        when(authenticationSecurityPort.authenticate(email, password)).thenReturn(user);
        when(authenticationSecurityPort.generateToken(user)).thenReturn(token);

        String result = authenticationUseCase.login(email, password);

        assertEquals(token, result);
    }

    @Test
    @DisplayName("El inicio de sesion debe lanzar AuthenticationException cuando las credenciales son invalidas")
    void loginCase2() {
        String email = "user@example.com";
        String password = "wrongPassword";

        when(authenticationSecurityPort.validateCredentials(email, password)).thenReturn(false);

        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            authenticationUseCase.login(email, password);
        });

        assertEquals(Util.INVALID_USER_CREDENTIALS, exception.getMessage());
    }
}