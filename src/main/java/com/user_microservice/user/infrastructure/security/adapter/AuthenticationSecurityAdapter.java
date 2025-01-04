package com.user_microservice.user.infrastructure.security.adapter;

import com.user_microservice.user.domain.exception.InvalidCredentialsException;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.security.IAuthenticationSecurityPort;
import com.user_microservice.user.domain.spi.IRoleModelPersistencePort;
import com.user_microservice.user.domain.util.Util;
import com.user_microservice.user.infrastructure.output.jpa.entity.UserEntity;
import com.user_microservice.user.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.user_microservice.user.infrastructure.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AuthenticationSecurityAdapter implements IAuthenticationSecurityPort {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSecurityAdapter.class);

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IRoleModelPersistencePort rolePersistencePort;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public UserModel authenticate(String email, String password) {
        logger.info("[Infraestructura] [AuthenticationSecurityAdapter] Iniciando autenticación.");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        UserModel userModel = userEntityMapper.userEntityToUserModel(userEntity);
        logger.info("[Infraestructura] [AuthenticationSecurityAdapter] Autenticación exitosa.");
        return userModel;
    }


    @Override
    public String generateToken(UserModel user) {
        logger.info("[Infraestructura] [AuthenticationSecurityAdapter] Generando token.");
        String token = jwtService.generateToken(user, generateExtraClaims(user));
        logger.info("[Infraestructura] [AuthenticationSecurityAdapter] Token generado exitosamente.");
        return token;
    }

    @Override
    public boolean validateCredentials(String userEmail, String userPassword) {
        logger.info("[Infraestructura] [AuthenticationSecurityAdapter] Validando credenciales.");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userEmail, userPassword)
            );
            boolean isAuthenticated = authentication.isAuthenticated();
            logger.info("[Infraestructura] [AuthenticationSecurityAdapter] Validación de credenciales exitosa.");
            return isAuthenticated;
        } catch (Exception e) {
            logger.warn("[Infraestructura] [AuthenticationSecurityAdapter] Credenciales inválidas.");
            throw new InvalidCredentialsException("Credenciales proporcionadas no validas.");
        }
    }

    private Map<String, Object> generateExtraClaims(UserModel user) {
        logger.info("[Infraestructura] [AuthenticationSecurityAdapter] Generando claims adicionales.");
        Map<String, Object> extraClaims = new HashMap<>();
        try {
            extraClaims.put("email", user.getEmail());
            RoleModel role = rolePersistencePort.getRoleByName(user.getRole().getName());
            extraClaims.put(Util.AUTHORITIES_KEY, Util.ROLE_PREFIX + role.getName().name());
            logger.info("[Infraestructura] [AuthenticationSecurityAdapter] Claims adicionales generados exitosamente.");
        } catch (Exception e) {
            logger.error("[Infraestructura] [AuthenticationSecurityAdapter] Error al generar claims adicionales.", e);
        }
        return extraClaims;
    }
}
