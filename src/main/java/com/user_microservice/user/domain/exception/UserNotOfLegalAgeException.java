package com.user_microservice.user.domain.exception;

public class UserNotOfLegalAgeException extends RuntimeException {
    public UserNotOfLegalAgeException(String message) {
        super(message);
    }
}