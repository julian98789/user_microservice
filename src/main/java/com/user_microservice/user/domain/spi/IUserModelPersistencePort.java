package com.user_microservice.user.domain.spi;

import com.user_microservice.user.domain.model.UserModel;

public interface IUserModelPersistencePort {

    UserModel registerUser(UserModel userModel);
    boolean existsUserByEmail(String email);
    boolean existsUserByIdentification(String identification);
}
