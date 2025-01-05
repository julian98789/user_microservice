package com.user_microservice.user.domain.spi;

import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.util.RoleName;

public interface IRoleModelPersistencePort {

    RoleModel getRoleByName(RoleName name);
}
