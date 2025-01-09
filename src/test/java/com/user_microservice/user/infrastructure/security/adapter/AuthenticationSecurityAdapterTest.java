package com.user_microservice.user.infrastructure.security.adapter;

import com.user_microservice.user.domain.exception.InvalidCredentialsException;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.util.RoleName;
import com.user_microservice.user.infrastructure.persistence.jpa.entity.UserEntity;
import com.user_microservice.user.infrastructure.persistence.jpa.mapper.IUserEntityMapper;
import com.user_microservice.user.infrastructure.security.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationSecurityAdapterTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @InjectMocks
    private AuthenticationSecurityAdapter authenticationSecurityAdapter;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Authenticate debe devolver UserModel cuando las credenciales son validas")
    void testAuthenticate() {
        String email = "user@example.com";
        String password = "password";
        UserEntity userEntity = new UserEntity();
        UserModel userModel = new UserModel(1L, new RoleModel(1L, RoleName.ADMIN, "admin"),
                "password", "test@example.com", LocalDate.of(2000, 1, 1),
                "123456789", "ID123", "Doe", "John");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userEntity);
        when(userEntityMapper.userEntityToUserModel(userEntity)).thenReturn(userModel);

        UserModel result = authenticationSecurityAdapter.authenticate(email, password);

        assertEquals(userModel, result);
    }

    @Test
    @DisplayName("GenerateToken debe devolver token")
    void testGenerateToken() {
        UserModel userModel = new UserModel(1L, new RoleModel(1L, RoleName.ADMIN, "admin"),
                "password", "test@example.com", LocalDate.of(2000, 1, 1),
                "123456789", "ID123", "Doe", "John");
        String token = "jwtToken";

        when(jwtService.generateToken(any(UserModel.class), any(Map.class))).thenReturn(token);

        String result = authenticationSecurityAdapter.generateToken(userModel);

        assertEquals(token, result);
    }

    @Test
    @DisplayName("ValidateCredentials debe devolver verdadero cuando las credenciales son validas")
    void testValidateCredentialsCase1() {
        String email = "user@example.com";
        String password = "password";
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        boolean result = authenticationSecurityAdapter.validateCredentials(email, password);

        assertTrue(result);
    }

    @Test
    @DisplayName("ValidateCredentials debe lanzar InvalidCredentialsException cuando las credenciales son invalidas")
    void testValidateCredentialsCase2() {
        String email = "user@example.com";
        String password = "wrongPassword";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new InvalidCredentialsException("Credenciales proporcionadas no validas."));

        assertThrows(InvalidCredentialsException.class, () -> {
            authenticationSecurityAdapter.validateCredentials(email, password);
        });
    }


}