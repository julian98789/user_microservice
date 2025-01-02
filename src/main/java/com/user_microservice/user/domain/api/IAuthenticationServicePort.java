package com.user_microservice.user.domain.api;

public interface IAuthenticationServicePort {
    String login(String email, String password);
}
