package com.user_microservice.user.application.mapper.usermapper;

import com.user_microservice.user.application.dto.userdto.UserResponse;
import com.user_microservice.user.application.mapper.rolemapper.IRoleResponseMapper;
import com.user_microservice.user.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = IRoleResponseMapper.class)
public interface IUserResponseMapper {

    @Mapping(target = "role", source = "role")
    UserResponse userModelToUserResponse(UserModel userModel);
}
