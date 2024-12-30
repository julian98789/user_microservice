package com.user_microservice.user.domain.usecase;

import com.user_microservice.user.domain.api.IRoleModelServicePort;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.model.RoleName;
import com.user_microservice.user.domain.exception.RoleNameNotFoundException;
import com.user_microservice.user.domain.spi.IRoleModelPersistencePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


public class RoleModelUseCase implements IRoleModelServicePort {

    private final IRoleModelPersistencePort roleModelPersistencePort;
    private static final Logger logger = LoggerFactory.getLogger(RoleModelUseCase.class);


    public RoleModelUseCase(IRoleModelPersistencePort roleModelPersistencePort) {
        this.roleModelPersistencePort = roleModelPersistencePort;
    }


    @Override
    public boolean existsRoleByName(String name) {

        logger.info("[Dominio] Iniciando verificación de existencia del rol");
        boolean isValidRole = Arrays.stream(RoleName.values())
                .anyMatch(role -> role.name().equalsIgnoreCase(name));

        if (!isValidRole) {
            throw new RoleNameNotFoundException("El rol '" + name + "' no existe.");
        }
        RoleName roleName = RoleName.valueOf(name.toUpperCase());
        RoleModel role = roleModelPersistencePort.getRoleByName(roleName);

        if (role == null) {
            throw new RoleNameNotFoundException("El rol '" + name + "' no existe.");
        }
        logger.info("[Dominio] Verificación de existencia del rol con nombre: {} completada exitosamente", name);
        return true;
    }
}
