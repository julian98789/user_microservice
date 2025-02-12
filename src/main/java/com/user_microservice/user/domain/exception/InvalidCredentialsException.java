package com.user_microservice.user.domain.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
