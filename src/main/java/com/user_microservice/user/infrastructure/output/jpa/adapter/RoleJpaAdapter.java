package com.user_microservice.user.infrastructure.output.jpa.adapter;

import com.user_microservice.user.domain.exception.RoleNameNotFoundException;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.model.RoleName;
import com.user_microservice.user.domain.spi.IRoleModelPersistencePort;
import com.user_microservice.user.domain.util.Util;
import com.user_microservice.user.infrastructure.output.jpa.entity.RoleEntity;
import com.user_microservice.user.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.user_microservice.user.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRoleModelPersistencePort {

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    @Override
    public RoleModel getRoleByName(RoleName name) {

        RoleEntity roleEntity = roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNameNotFoundException(Util.ROLE_NOT_FUND));

        return roleEntityMapper.roleEntityToRoleModel(roleEntity);
    }
}
