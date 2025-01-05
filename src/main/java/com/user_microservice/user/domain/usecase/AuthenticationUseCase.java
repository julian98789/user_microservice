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
        logger.info("[Dominio] Iniciando proceso de login para el email: {}", email);

        if (!authenticationSecurityPort.validateCredentials(email, password)) {
            logger.warn("[Dominio] Credenciales invalidas para el email: {}", email);
            throw new AuthenticationException(Util.INVALID_USER_CREDENTIALS);
        }

        UserModel user = authenticationSecurityPort.authenticate(email, password);
        logger.info("[Dominio] Usuario autenticado exitosamente para el email: {}", email);

        logger.info("[Dominio] Generando token JWT para el usuario con email: {}", email);
        return authenticationSecurityPort.generateToken(user);
    }
}
