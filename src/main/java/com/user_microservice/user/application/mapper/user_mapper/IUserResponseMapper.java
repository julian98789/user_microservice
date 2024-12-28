package com.user_microservice.user.application.mapper.user_mapper;

import com.user_microservice.user.application.dto.user_dto.UserResponse;
import com.user_microservice.user.application.mapper.role_mapper.IRoleResponseMapper;
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
