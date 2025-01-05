package com.user_microservice.user.domain.util;

public class Util {
    public static final String NAME_NOT_BLANK = "El nombre no puede estar vacio";
    public static final String NAME_ROLE_NOT_BLANK = "El nombre del rol no puede estar vacio";
    public static final String LAST_NAME_NOT_BLANK = "El apellido no puede estar vacio";
    public static final String IDENTIFICATION_NOT_BLANK = "El documento de identidad no puede estar vacio";
    public static final String IDENTIFICATION_PATTERN = "El documento de identidad debe ser numerico";
    public static final String PHONE_NOT_BLANK = "El telefono no puede estar vacio";
    public static final String PHONE_PATTERN = "El telefono no es valido";
    public static final String DATE_OF_BIRTH_NOT_NULL = "La fecha de nacimiento no puede estar vacia";
    public static final String EMAIL_NOT_BLANK = "El email no puede estar vacio";
    public static final String EMAIL_PATTERN = "El email no es valido";
    public static final String PASSWORD_NOT_BLANK = "La contrasena no puede estar vacia";
    public static final String USER_NOT_OF_LEGAL_EGE = "El usuario debe ser mayor de edad";
    public static final String USER_IDENTIFICATION_ALREADY_EXISTS = "La IDENTIFICATION ya existe";
    public static final String USER_EMAIL_ALREADY_EXISTS = "el EMAIL ya existe";
    public static final String ROLE_NOT_FUND = "Rol no encontrado";
    public static final String INVALID_USER_CREDENTIALS  = "Usuario con credenciales invalidas";
    public static final long TOKEN_EXPIRATION_TIME =(long) 1000 * 60 * 60 * 10;
    public static final String AUTHORITIES_KEY = "authorities";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String USER_NOT_FOUND = "Uauario no encontrado";
    public static final int TOKEN_PREFIX_LENGTH = 7;
    public static final String INVALID_TOKEN = "Token invalido";
    public static final String TOKEN_EXPIRED = "El token ha expirado";
    private Util(){}
}
