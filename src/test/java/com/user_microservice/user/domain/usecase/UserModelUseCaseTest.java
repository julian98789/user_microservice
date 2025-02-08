package com.user_microservice.user.domain.usecase;

import com.user_microservice.user.domain.exception.EmailAlreadyExistsException;
import com.user_microservice.user.domain.exception.IdentificationAlreadyExistsException;
import com.user_microservice.user.domain.exception.UserNotOfLegalAgeException;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.spi.IUserModelPersistencePort;
import com.user_microservice.user.domain.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserModelUseCaseTest {

    @Mock
    private IUserModelPersistencePort userModelPersistencePort;

    @InjectMocks
    private UserModelUseCase userModelUseCase;

    private UserModel userModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userModel = new UserModel();
        userModel.setIdentification("123");
        userModel.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userModel.setEmail("user@example.co");

    }

    @Test
    @DisplayName("Given valid user data, when registering user, then return registered user")
    void givenValidUserData_whenRegisteringUser_thenReturnRegisteredUser() {

        when(userModelPersistencePort.registerUser(userModel)).thenReturn(userModel);

        UserModel result = userModelUseCase.registerUser(userModel);

        assertNotNull(result);
        assertEquals(userModel, result);

        verify(userModelPersistencePort, times(1)).registerUser(userModel);
    }

    @Test
    @DisplayName("Given underage user, when registering user, then throw UserNotOfLegalAgeException")
    void givenUnderageUser_whenRegisteringUser_thenThrowUserNotOfLegalAgeException() {

        userModel.setDateOfBirth(LocalDate.now());

        UserNotOfLegalAgeException exception = assertThrows(UserNotOfLegalAgeException.class,
                () -> userModelUseCase.registerUser(userModel));

        assertEquals(Util.USER_NOT_OF_LEGAL_EGE, exception.getMessage());
        verify(userModelPersistencePort, never()).registerUser(any());
    }

    @Test
    @DisplayName("Given existing email, when registering user, then throw EmailAlreadyExistsException")
    void givenExistingEmail_whenRegisteringUser_thenThrowEmailAlreadyExistsException() {

        when(userModelPersistencePort.existsUserByEmail(userModel.getEmail())).thenReturn(true);

        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class,
                () -> userModelUseCase.registerUser(userModel));

        assertEquals(Util.USER_EMAIL_ALREADY_EXISTS, exception.getMessage());
        verify(userModelPersistencePort, never()).registerUser(any());
    }

    @Test
    @DisplayName("Given existing identification, when registering user, then throw IdentificationAlreadyExistsException")
    void givenExistingIdentification_whenRegisteringUser_thenThrowIdentificationAlreadyExistsException() {

        when(userModelPersistencePort.existsUserByIdentification(userModel.getIdentification())).thenReturn(true);

        IdentificationAlreadyExistsException exception = assertThrows(IdentificationAlreadyExistsException.class,
                () -> userModelUseCase.registerUser(userModel));

        assertEquals(Util.USER_IDENTIFICATION_ALREADY_EXISTS, exception.getMessage());
        verify(userModelPersistencePort, never()).registerUser(any());
    }
}