package com.user_microservice.user.domain.security;

import com.user_microservice.user.domain.model.UserModel;

public interface IAuthenticationSecurityPort {
    UserModel authenticate(String email, String password);
    String generateToken(UserModel user);
    boolean validateCredentials(String userEmail, String userPassword);
}
