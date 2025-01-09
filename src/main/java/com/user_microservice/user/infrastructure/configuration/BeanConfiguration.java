package com.user_microservice.user.infrastructure.configuration;

import com.user_microservice.user.domain.api.IAuthenticationServicePort;
import com.user_microservice.user.domain.api.IRoleModelServicePort;
import com.user_microservice.user.domain.api.IUserModelServicePort;
import com.user_microservice.user.domain.security.IAuthenticationSecurityPort;
import com.user_microservice.user.domain.spi.IRoleModelPersistencePort;
import com.user_microservice.user.domain.spi.IUserModelPersistencePort;
import com.user_microservice.user.domain.usecase.AuthenticationUseCase;
import com.user_microservice.user.domain.usecase.RoleModelUseCase;
import com.user_microservice.user.domain.usecase.UserModelUseCase;
import com.user_microservice.user.infrastructure.persistence.jpa.adapter.RoleJpaAdapter;
import com.user_microservice.user.infrastructure.persistence.jpa.adapter.UserJpaAdapter;
import com.user_microservice.user.infrastructure.persistence.jpa.mapper.IRoleEntityMapper;
import com.user_microservice.user.infrastructure.persistence.jpa.mapper.IUserEntityMapper;
import com.user_microservice.user.infrastructure.persistence.jpa.repository.IRoleRepository;
import com.user_microservice.user.infrastructure.persistence.jpa.repository.IUserRepository;
import com.user_microservice.user.infrastructure.security.adapter.AuthenticationSecurityAdapter;
import com.user_microservice.user.infrastructure.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Bean
    public IRoleModelPersistencePort roleModelPersistencePort() {
        return new RoleJpaAdapter(roleRepository,roleEntityMapper);
    }

    @Bean
    public IUserModelPersistencePort userModelPersistencePort() {
        return new UserJpaAdapter(userRepository,userEntityMapper,passwordEncoder);
    }

    @Bean
    public IUserModelServicePort userModelServicePort(IUserModelPersistencePort userModelPersistencePort) {
        return new UserModelUseCase(userModelPersistencePort);
    }

    @Bean
    public IRoleModelServicePort roleModelServicePort(IRoleModelPersistencePort roleModelPersistencePort) {
        return new RoleModelUseCase(roleModelPersistencePort);
    }

    @Bean
    public IAuthenticationSecurityPort authenticationSecurityPort (IRoleModelPersistencePort roleModelPersistencePort){
        return new AuthenticationSecurityAdapter( authenticationManager, jwtService, roleModelPersistencePort, userEntityMapper);
    }

    @Bean
    public IAuthenticationServicePort authenticationServicePort(IAuthenticationSecurityPort authenticationSecurityPort){
       return new AuthenticationUseCase(authenticationSecurityPort);
    }



}
