package com.user_microservice.user.application.handler.userhandler;

import com.user_microservice.user.application.dto.userdto.UserRequest;
import com.user_microservice.user.application.dto.userdto.UserResponse;
import com.user_microservice.user.application.mapper.usermapper.IUserRequestMapper;
import com.user_microservice.user.application.mapper.usermapper.IUserResponseMapper;
import com.user_microservice.user.domain.api.IRoleModelServicePort;
import com.user_microservice.user.domain.api.IUserModelServicePort;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.util.RoleName;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.spi.IRoleModelPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserHandlerTest {

    @Mock
    private IRoleModelServicePort roleModelServicePort;

    @Mock
    private IUserModelServicePort userModelServicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @Mock
    private IUserResponseMapper userResponseMapper;

    @Mock
    private IRoleModelPersistencePort roleModelPersistencePort;

    @InjectMocks
    private UserHandler userHandler;

    private UserRequest userRequest;
    private RoleModel roleModel;
    private UserModel userModel;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userRequest = new UserRequest();
        userRequest.setRole("ADMIN");

        roleModel = new RoleModel();

        userModel = new UserModel();
        userModel.setEmail("julian@mail.com");
        userModel.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userModel.setIdentification("123456789");

        userResponse = new UserResponse();
    }

    @Test
    @DisplayName("Given valid user data, when registering user, then return registered user")
    void givenValidUserData_whenRegisteringUser_thenReturnRegisteredUser() {

        when(roleModelPersistencePort.getRoleByName(RoleName.ADMIN)).thenReturn(roleModel);
        when(userRequestMapper.userRequestToUserModel(userRequest)).thenReturn(userModel);
        when(userModelServicePort.registerUser(userModel)).thenReturn(userModel);
        when(userResponseMapper.userModelToUserResponse(userModel)).thenReturn(userResponse);

        UserResponse result = userHandler.registerUser(userRequest);

        assertNotNull(result);
        assertEquals(userResponse, result);
        verify(roleModelServicePort, times(1)).existsRoleByName("ADMIN");
        verify(roleModelPersistencePort, times(1)).getRoleByName(RoleName.ADMIN);
        verify(userRequestMapper, times(1)).userRequestToUserModel(userRequest);
        verify(userModelServicePort, times(1)).registerUser(userModel);
        verify(userResponseMapper, times(1)).userModelToUserResponse(userModel);
    }
}