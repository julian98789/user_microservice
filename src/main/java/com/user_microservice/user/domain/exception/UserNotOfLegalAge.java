package com.user_microservice.user.domain.exception;

public class UserNotOfLegalAge extends RuntimeException {
    public UserNotOfLegalAge(String message) {
        super(message);
    }
}