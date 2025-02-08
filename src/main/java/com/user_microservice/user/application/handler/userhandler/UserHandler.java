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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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



    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        roleModelServicePort.existsRoleByName(userRequest.getRole());

        String roleNameString = userRequest.getRole().toUpperCase();
        RoleName roleName = RoleName.valueOf(roleNameString);

        RoleModel roleModel = roleModelPersistencePort.getRoleByName(roleName);

        UserModel userModel = userRequestMapper.userRequestToUserModel(userRequest);
        userModel.setRole(roleModel);

        UserModel registeredUser = userModelServicePort.registerUser(userModel);

        return userResponseMapper.userModelToUserResponse(registeredUser);
    }

}
