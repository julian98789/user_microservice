package com.user_microservice.user.application.handler;

import com.user_microservice.user.application.dto.user_dto.UserRequest;
import com.user_microservice.user.application.dto.user_dto.UserResponse;
import com.user_microservice.user.application.mapper.user_mapper.IUserRequestMapper;
import com.user_microservice.user.application.mapper.user_mapper.IUserResponseMapper;
import com.user_microservice.user.domain.api.IUserModelServicePort;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.model.RoleName;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.spi.IRoleModelPersistencePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler{

     private final IUserModelServicePort userModelServicePort;
     private final IUserRequestMapper userRequestMapper;
     private final IUserResponseMapper userResponseMapper;
     private final IRoleModelPersistencePort roleModelPersistencePort;

    @Override
    public UserResponse registerUser(UserRequest userRequest) {

        // Convertir el String role a RoleName
        String roleNameString = userRequest.getRole().toUpperCase();
        RoleName roleName = RoleName.valueOf(roleNameString);

        // Obtener el RoleModel basado en RoleName
        RoleModel roleModel = roleModelPersistencePort.getRoleByName(roleName);

        // Convertir el UserRequest a UserModel y asignar el RoleModel
        UserModel userModel = userRequestMapper.UserRequestToUserModel(userRequest);
        userModel.setRole(roleModel);

        // Registrar el usuario
        UserModel registeredUser = userModelServicePort.registerUser(userModel);

        // Retornar la respuesta
        return userResponseMapper.UserModelToUserResponse(registeredUser);
    }

}
