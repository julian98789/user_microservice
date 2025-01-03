package com.user_microservice.user.domain.usecase;

import com.user_microservice.user.domain.api.IAuthenticationServicePort;
import com.user_microservice.user.domain.exception.AuthenticationException;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.security.IAuthenticationSecurityPort;
import com.user_microservice.user.domain.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationUseCase implements IAuthenticationServicePort {

    private final IAuthenticationSecurityPort authenticationSecurityPort;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationUseCase.class);

    public AuthenticationUseCase(IAuthenticationSecurityPort authenticationSecurityPort) {
        this.authenticationSecurityPort = authenticationSecurityPort;
    }

    @Override
    public String login(String email, String password) {
        logger.info("[Dominio] Entre al Login");
        if (!authenticationSecurityPort.validateCredentials(email, password)) {
            throw new AuthenticationException(Util.INVALID_USER_CREDENTIALS);
        }

        UserModel user = authenticationSecurityPort.authenticate(email, password);

        logger.info("[Dominio] Generando token para el usuario");
        return authenticationSecurityPort.generateToken(user);
    }
}
