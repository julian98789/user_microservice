package com.user_microservice.user.domain.spi;

import com.user_microservice.user.domain.model.RoleModel;

public interface IRoleModelPersistencePort {

    RoleModel getRoleByName(String name);
}
