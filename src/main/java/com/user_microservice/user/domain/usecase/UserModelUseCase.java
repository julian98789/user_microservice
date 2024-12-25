package com.user_microservice.user.domain.usecase;

import com.user_microservice.user.domain.api.IUserModelServicePort;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.spi.IUserModelPersistencePort;

import java.time.LocalDate;

public class UserModelUseCase implements IUserModelServicePort {

    private final IUserModelPersistencePort userModelPersistencePort;

    public UserModelUseCase(IUserModelPersistencePort userModelPersistencePort) {
        this.userModelPersistencePort = userModelPersistencePort;
    }

    @Override
    public UserModel registerUser(UserModel userModel) {
        return null;
    }

}
