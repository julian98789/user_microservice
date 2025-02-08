package com.user_microservice.user.infrastructure.persistence.jpa.adapter;

import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.spi.IUserModelPersistencePort;
import com.user_microservice.user.infrastructure.persistence.jpa.entity.UserEntity;
import com.user_microservice.user.infrastructure.persistence.jpa.mapper.IUserEntityMapper;
import com.user_microservice.user.infrastructure.persistence.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserModelPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserModel registerUser(UserModel userModel) {

        String encoderPassword = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(encoderPassword);

        UserEntity userEntity = userEntityMapper.userModelToUserEntity(userModel);
        userEntity = userRepository.save(userEntity);

        return userEntityMapper.userEntityToUserModel(userEntity);
    }

    @Override
    public boolean existsUserByEmail(String email) {

        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean existsUserByIdentification(String identification) {

        return userRepository.findByIdentification(identification).isPresent();
    }
}
