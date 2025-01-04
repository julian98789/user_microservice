package com.user_microservice.user.infrastructure.output.jpa.adapter;

import com.user_microservice.user.domain.exception.RoleNameNotFoundException;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.util.RoleName;
import com.user_microservice.user.domain.spi.IRoleModelPersistencePort;
import com.user_microservice.user.domain.util.Util;
import com.user_microservice.user.infrastructure.output.jpa.entity.RoleEntity;
import com.user_microservice.user.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.user_microservice.user.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRoleModelPersistencePort {

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    private static final Logger logger = LoggerFactory.getLogger(RoleJpaAdapter.class);

    @Override
    public RoleModel getRoleByName(RoleName name) {
        logger.info("[Infraestructura] Recibiendo solicitud para verificar existencia del nombre del rol");

        RoleEntity roleEntity = roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNameNotFoundException(Util.ROLE_NOT_FUND));

        logger.info("[Infraestructura] Retornando respuesta de existencia del nombre del rol");
        return roleEntityMapper.roleEntityToRoleModel(roleEntity);
    }
}
