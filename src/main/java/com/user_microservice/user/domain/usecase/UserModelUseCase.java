package com.user_microservice.user.domain.usecase;

import com.user_microservice.user.domain.api.IUserModelServicePort;
import com.user_microservice.user.domain.exception.EmailAlreadyExistsException;
import com.user_microservice.user.domain.exception.IdentificationAlreadyExistsException;
import com.user_microservice.user.domain.exception.UserNotOfLegalAgeException;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.spi.IUserModelPersistencePort;
import com.user_microservice.user.domain.util.Util;

import java.time.LocalDate;


public class UserModelUseCase implements IUserModelServicePort {

    private final IUserModelPersistencePort userModelPersistencePort;

    public UserModelUseCase(IUserModelPersistencePort userModelPersistencePort) {
        this.userModelPersistencePort = userModelPersistencePort;
    }

    @Override
    public UserModel registerUser(UserModel userModel) {

        validateUser(userModel);

        return userModelPersistencePort.registerUser(userModel);
    }

    private void validateUser(UserModel user) {

        if (LocalDate.now().minusYears(18).isBefore(user.getDateOfBirth())) {
            throw new UserNotOfLegalAgeException(Util.USER_NOT_OF_LEGAL_EGE);
        }
        if (userModelPersistencePort.existsUserByIdentification(user.getIdentification())) {
            throw new IdentificationAlreadyExistsException(Util.USER_IDENTIFICATION_ALREADY_EXISTS);
        }
        if (userModelPersistencePort.existsUserByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(Util.USER_EMAIL_ALREADY_EXISTS);
        }

    }

}
