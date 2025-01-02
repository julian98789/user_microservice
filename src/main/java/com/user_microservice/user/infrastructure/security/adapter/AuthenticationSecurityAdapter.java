package com.user_microservice.user.infrastructure.security.adapter;

import com.user_microservice.user.application.mapper.user_mapper.IUserResponseMapper;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.security.IAuthenticationSecurityPort;
import com.user_microservice.user.domain.spi.IRoleModelPersistencePort;
import com.user_microservice.user.domain.util.Util;
import com.user_microservice.user.infrastructure.output.jpa.entity.UserEntity;
import com.user_microservice.user.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.user_microservice.user.infrastructure.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AuthenticationSecurityAdapter implements IAuthenticationSecurityPort {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IRoleModelPersistencePort rolePersistencePort;
    private final IUserEntityMapper userEntityMapper; // Mapper de entidades

    @Override
    public UserModel authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        // Obtenemos el UserEntity después de la autenticación
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        // Convertimos UserEntity a UserModel
        UserModel userModel = userEntityMapper.userEntityToUserModel(userEntity);

        // Usamos el mapper de respuesta para convertir UserModel a UserResponse
        return userModel;
    }

    @Override
    public String generateToken(UserModel user) {
        return jwtService.generateToken(user, generateExtraClaims(user));
    }

    @Override
    public boolean validateCredentials(String userEmail, String userPassword) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userEmail, userPassword)
            );
            return authentication.isAuthenticated();
        } catch (Exception e) {
            return false;
        }
    }

    private Map<String, Object> generateExtraClaims(UserModel user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", user.getEmail());
        RoleModel role = rolePersistencePort.getRoleByName(user.getRole().getName());
        extraClaims.put(Util.AUTHORITIES_KEY, Util.ROLE_PREFIX + role.getName().name());
        return extraClaims;
    }
}
