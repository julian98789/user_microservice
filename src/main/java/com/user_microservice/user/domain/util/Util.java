package com.user_microservice.user.domain.util;

public class Util {
    public static final String NAME_NOT_BLANK = "The name cannot be empty";
    public static final String NAME_ROLE_NOT_BLANK = "The role name cannot be empty";
    public static final String LAST_NAME_NOT_BLANK = "The last name cannot be empty";
    public static final String IDENTIFICATION_NOT_BLANK = "The identification document cannot be empty";
    public static final String IDENTIFICATION_PATTERN = "The identification document must be numeric";
    public static final String PHONE_NOT_BLANK = "The phone number cannot be empty";
    public static final String PHONE_PATTERN = "The phone number is not valid";
    public static final String DATE_OF_BIRTH_NOT_NULL = "The date of birth cannot be empty";
    public static final String EMAIL_NOT_BLANK = "The email cannot be empty";
    public static final String EMAIL_PATTERN = "The email is not valid";
    public static final String PASSWORD_NOT_BLANK = "The password cannot be empty";
    public static final String USER_NOT_OF_LEGAL_EGE = "The user must be of legal age";
    public static final String USER_IDENTIFICATION_ALREADY_EXISTS = "The identification already exists";
    public static final String USER_EMAIL_ALREADY_EXISTS = "The email already exists";
    public static final String ROLE_NOT_FUND = "Role not found";
    public static final String INVALID_USER_CREDENTIALS = "User with invalid credentials";
    public static final long TOKEN_EXPIRATION_TIME = (long) 1000 * 60 * 60 * 10;
    public static final String AUTHORITIES_KEY = "authorities";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String USER_NOT_FOUND = "User not found";
    public static final int TOKEN_PREFIX_LENGTH = 7;
    public static final String INVALID_TOKEN = "Invalid token";
    public static final String TOKEN_EXPIRED = "The token has expired";
    public static final String EXTRA_CLAIMS_ERROR = "Error generating extra claims";
    public static final String ROLE_NAME_NOT_FOUND = "Role name not found";

    private Util() {}
}
