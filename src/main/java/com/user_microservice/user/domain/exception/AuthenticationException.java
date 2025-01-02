package com.user_microservice.user.domain.exception;


public class AuthenticationException extends  RuntimeException{
    public AuthenticationException(String message) {
        super(message);
    }
}
