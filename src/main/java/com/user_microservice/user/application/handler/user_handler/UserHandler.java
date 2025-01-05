package com.user_microservice.user.application.handler.user_handler;

import com.user_microservice.user.application.dto.user_dto.UserRequest;
import com.user_microservice.user.application.dto.user_dto.UserResponse;
import com.user_microservice.user.application.mapper.user_mapper.IUserRequestMapper;
import com.user_microservice.user.application.mapper.user_mapper.IUserResponseMapper;
import com.user_microservice.user.domain.api.IRoleModelServicePort;
import com.user_microservice.user.domain.api.IUserModelServicePort;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.util.RoleName;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.spi.IRoleModelPersistencePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IRoleModelServicePort roleModelServicePort;
     private final IUserModelServicePort userModelServicePort;
     private final IUserRequestMapper userRequestMapper;
     private final IUserResponseMapper userResponseMapper;
     private final IRoleModelPersistencePort roleModelPersistencePort;
    private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);


    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        logger.info("[Aplicación] Iniciando registro de usuario en el sistema");
        roleModelServicePort.existsRoleByName(userRequest.getRole());

        String roleNameString = userRequest.getRole().toUpperCase();
        RoleName roleName = RoleName.valueOf(roleNameString);

        RoleModel roleModel = roleModelPersistencePort.getRoleByName(roleName);

        UserModel userModel = userRequestMapper.userRequestToUserModel(userRequest);
        userModel.setRole(roleModel);

        UserModel registeredUser = userModelServicePort.registerUser(userModel);

        logger.info("[Aplicación] mapping de modelo a respuesta completado");
        return userResponseMapper.userModelToUserResponse(registeredUser);
    }

}
