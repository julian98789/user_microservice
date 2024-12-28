package com.user_microservice.user.application.mapper.user_mapper;

import com.user_microservice.user.application.dto.user_dto.UserRequest;
import com.user_microservice.user.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {

    @Mapping(target = "role.name", source = "role")  // Mapear el String 'role' a RoleName
    UserModel userRequestToUserModel(UserRequest userRequest);

}
