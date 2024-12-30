package com.user_microservice.user.domain.api;

import com.user_microservice.user.domain.model.UserModel;

public interface IUserModelServicePort {

    UserModel registerUser(UserModel userModel);
}
