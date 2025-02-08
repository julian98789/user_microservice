package com.user_microservice.user.infrastructure.security.adapter;

import com.user_microservice.user.domain.exception.ExtraClaimsException;
import com.user_microservice.user.domain.exception.InvalidCredentialsException;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.security.IAuthenticationSecurityPort;
import com.user_microservice.user.domain.spi.IRoleModelPersistencePort;
import com.user_microservice.user.domain.util.Util;
import com.user_microservice.user.infrastructure.persistence.jpa.entity.UserEntity;
import com.user_microservice.user.infrastructure.persistence.jpa.mapper.IUserEntityMapper;
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
    private final IUserEntityMapper userEntityMapper;

    @Override
    public UserModel authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        return userEntityMapper.userEntityToUserModel(userEntity);
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
            throw new InvalidCredentialsException(Util.INVALID_USER_CREDENTIALS, e);
        }
    }

    private Map<String, Object> generateExtraClaims(UserModel user) {
        Map<String, Object> extraClaims = new HashMap<>();
        try {
            extraClaims.put("email", user.getEmail());
            RoleModel role = rolePersistencePort.getRoleByName(user.getRole().getName());
            extraClaims.put(Util.AUTHORITIES_KEY, Util.ROLE_PREFIX + role.getName().name());
        } catch (Exception e) {
            throw new ExtraClaimsException(Util.EXTRA_CLAIMS_ERROR, e);
        }
        return extraClaims;
    }
}
