package com.user_microservice.user.domain.exception;

public class IdentificationAlreadyExistsException extends RuntimeException {
    public IdentificationAlreadyExistsException(String message) {
        super(message);
    }
}