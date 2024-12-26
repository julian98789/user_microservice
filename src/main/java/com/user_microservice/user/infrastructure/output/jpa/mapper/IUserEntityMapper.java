package com.user_microservice.user.infrastructure.output.jpa.mapper;


import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.infrastructure.output.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserEntityMapper {

    @Mapping(target = "role", source = "role")
    UserEntity userModelToUserEntity(UserModel userModel);

    @Mapping(target = "role", source = "role")
    UserModel userEntityToUserModel(UserEntity userEntity);
}
