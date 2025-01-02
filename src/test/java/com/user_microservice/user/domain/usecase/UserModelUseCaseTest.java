package com.user_microservice.user.domain.usecase;

import com.user_microservice.user.domain.exception.EmailAlreadyExistsException;
import com.user_microservice.user.domain.exception.IdentificationAlreadyExistsException;
import com.user_microservice.user.domain.exception.UserNotOfLegalAgeException;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.model.RoleName;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Registrar usuario con datos válidos debe devolver el usuario registrado")
    void registerUserCaseTest1() {
        UserModel userModel = new UserModel(1L, new RoleModel(1L, RoleName.ADMIN, "admin"),
                "password", "test@example.com", LocalDate.of(2000, 1, 1),
                "123456789", "ID123", "Doe", "John");
        when(userModelPersistencePort.registerUser(userModel)).thenReturn(userModel);

        UserModel result = userModelUseCase.registerUser(userModel);

        assertNotNull(result);
        assertEquals(userModel, result);
        verify(userModelPersistencePort, times(1)).registerUser(userModel);
    }

    @Test
    @DisplayName("Registrar usuario menor de edad debe lanzar UserNotOfLegalAge")
    void registerUserCaseTest2() {
        UserModel userModel = new UserModel(1L, new RoleModel(1L, RoleName.ADMIN, "admin"),
                "password", "test@example.com", LocalDate.of(2020, 1, 1),
                "123456789", "ID123", "Doe", "John");

        UserNotOfLegalAgeException exception = assertThrows(UserNotOfLegalAgeException.class, () -> userModelUseCase.registerUser(userModel));
        assertEquals(Util.USER_NOT_OF_LEGAL_EGE, exception.getMessage());
        verify(userModelPersistencePort, never()).registerUser(any());
    }

    @Test
    @DisplayName("Registrar usuario con email existente debe lanzar EmailAlreadyExistsException")
    void registerUserCaseTest3() {
        UserModel userModel = new UserModel(1L, new RoleModel(1L, RoleName.ADMIN, "admin"),
                "password", "test@example.com", LocalDate.of(2000, 1, 1),
                "123456789", "ID123", "Doe", "John");
        when(userModelPersistencePort.existsUserByEmail(userModel.getEmail())).thenReturn(true);

        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> userModelUseCase.registerUser(userModel));
        assertEquals(Util.USER_EMAIL_ALREADY_EXISTS, exception.getMessage());
        verify(userModelPersistencePort, never()).registerUser(any());
    }

    @Test
    @DisplayName("Registrar usuario con identificación existente debe lanzar IdentificationAlreadyExistsException")
    void registerUserCaseTest4() {
        UserModel userModel = new UserModel(1L, new RoleModel(1L, RoleName.ADMIN, "admin"),
                "password", "test@example.com", LocalDate.of(2000, 1, 1),
                "123456789", "ID123", "Doe", "John");
        when(userModelPersistencePort.existsUserByIdentification(userModel.getIdentification())).thenReturn(true);

        IdentificationAlreadyExistsException exception = assertThrows(IdentificationAlreadyExistsException.class, () -> userModelUseCase.registerUser(userModel));
        assertEquals(Util.USER_IDENTIFICATION_ALREADY_EXISTS, exception.getMessage());
        verify(userModelPersistencePort, never()).registerUser(any());
    }
}