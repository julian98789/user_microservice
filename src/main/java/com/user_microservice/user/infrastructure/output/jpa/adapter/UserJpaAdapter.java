package com.user_microservice.user.infrastructure.output.jpa.adapter;

import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.spi.IUserModelPersistencePort;
import com.user_microservice.user.infrastructure.output.jpa.entity.UserEntity;
import com.user_microservice.user.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.user_microservice.user.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserModelPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserJpaAdapter.class);

    @Override
    public UserModel registerUser(UserModel userModel) {
        logger.info("[Infraestructura] Recibiendo solicitud para guardar el usuario");

        String encoderPassword = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(encoderPassword);

        UserEntity userEntity = userEntityMapper.userModelToUserEntity(userModel);

        userEntity = userRepository.save(userEntity);

        logger.info("[Infraestructura] Mapeo de entidad a modelo completado");
        return userEntityMapper.userEntityToUserModel(userEntity);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        logger.info("[Infraestructura] Recibiendo solicitud para verificar existencia del email");

        logger.info("[Infraestructura] Retornando respuesta de existencia del email");
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean existsUserByIdentification(String identification) {
        logger.info("[Infraestructura] Recibiendo solicitud para verificar existencia dela identificación");

        logger.info("[Infraestructura] Retornando respuesta de existencia de la identificación");
        return userRepository.findByIdentification(identification).isPresent();
    }
}
