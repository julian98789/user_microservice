package com.user_microservice.user.domain.exception;

public class ExtraClaimsException extends RuntimeException {
    public ExtraClaimsException(String message, Throwable cause) {
        super(message, cause);
    }
}