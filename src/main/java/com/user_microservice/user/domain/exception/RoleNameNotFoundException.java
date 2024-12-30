package com.user_microservice.user.domain.exception;

public class RoleNameNotFoundException extends RuntimeException {
    public RoleNameNotFoundException(String message) {
        super(message);
    }
}