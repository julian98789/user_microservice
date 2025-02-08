package com.user_microservice.user.domain.usecase;

import com.user_microservice.user.domain.api.IRoleModelServicePort;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.util.RoleName;
import com.user_microservice.user.domain.exception.RoleNameNotFoundException;
import com.user_microservice.user.domain.spi.IRoleModelPersistencePort;
import com.user_microservice.user.domain.util.Util;
import java.util.Arrays;


public class RoleModelUseCase implements IRoleModelServicePort {

    private final IRoleModelPersistencePort roleModelPersistencePort;

    public RoleModelUseCase(IRoleModelPersistencePort roleModelPersistencePort) {
        this.roleModelPersistencePort = roleModelPersistencePort;
    }


    @Override
    public boolean existsRoleByName(String name) {

        boolean isValidRole = Arrays.stream(RoleName.values())
                .anyMatch(role -> role.name().equalsIgnoreCase(name));

        if (!isValidRole) {
            throw new RoleNameNotFoundException(Util.ROLE_NAME_NOT_FOUND);
        }
        RoleName roleName = RoleName.valueOf(name.toUpperCase());
        RoleModel role = roleModelPersistencePort.getRoleByName(roleName);

        if (role == null) {
            throw new RoleNameNotFoundException(Util.ROLE_NAME_NOT_FOUND);
        }
        return true;
    }
}
