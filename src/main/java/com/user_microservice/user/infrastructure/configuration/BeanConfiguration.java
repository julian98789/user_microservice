package com.user_microservice.user.infrastructure.configuration;

import com.user_microservice.user.domain.api.IUserModelServicePort;
import com.user_microservice.user.domain.spi.IRoleModelPersistencePort;
import com.user_microservice.user.domain.spi.IUserModelPersistencePort;
import com.user_microservice.user.domain.usecase.UserModelUseCase;
import com.user_microservice.user.infrastructure.output.jpa.adapter.RoleJpaAdapter;
import com.user_microservice.user.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.user_microservice.user.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.user_microservice.user.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.user_microservice.user.infrastructure.output.jpa.repository.IRoleRepository;
import com.user_microservice.user.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;
    private final PasswordEncoder passwordEncoder;

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



}
