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

    @Mock
    Authentication authentication;


    @InjectMocks
    private AuthenticationSecurityAdapter authenticationSecurityAdapter;

    private UserEntity userEntity;
    private UserModel userModel;

    private String email;
    private String password;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        email = "user@example.com";
        password = "password";
        userEntity = new UserEntity();
        userModel = new UserModel();

    }
    @Test
    @DisplayName("Given valid credentials, when authenticate, then return UserModel")
    void givenValidCredentials_whenAuthenticate_thenReturnUserModel() {

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userEntity);
        when(userEntityMapper.userEntityToUserModel(userEntity)).thenReturn(userModel);

        UserModel result = authenticationSecurityAdapter.authenticate(email, password);

        assertEquals(userModel, result);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(authentication, times(1)).getPrincipal();
        verify(userEntityMapper, times(1)).userEntityToUserModel(userEntity);
    }

    @Test
    @DisplayName("Given UserModel, when generateToken, then return token")
    void givenUserModel_whenGenerateToken_thenReturnToken() {

        String token = "jwtToken";

        when(jwtService.generateToken(any(UserModel.class), any(Map.class))).thenReturn(token);

        String result = authenticationSecurityAdapter.generateToken(userModel);

        assertEquals(token, result);

        verify(jwtService, times(1)).generateToken(any(UserModel.class), any(Map.class));
    }

    @Test
    @DisplayName("Given valid credentials, when validateCredentials, then return true")
    void givenValidCredentials_whenValidateCredentials_thenReturnTrue() {

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        boolean result = authenticationSecurityAdapter.validateCredentials(email, password);
        assertTrue(result);

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("Given invalid credentials, when validateCredentials, then throw InvalidCredentialsException")
    void givenInvalidCredentials_whenValidateCredentials_thenThrowInvalidCredentialsException() {

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new InvalidCredentialsException("Credenciales proporcionadas no validas."));

        assertThrows(InvalidCredentialsException.class, () -> {
            authenticationSecurityAdapter.validateCredentials(email, password);
        });

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}