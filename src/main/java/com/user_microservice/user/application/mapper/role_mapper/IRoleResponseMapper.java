package com.user_microservice.user.application.mapper.role_mapper;

import com.user_microservice.user.application.dto.role_dto.role_dto.RoleResponse;
import com.user_microservice.user.domain.model.RoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IRoleResponseMapper {

    RoleResponse roleModelToRoleResponse(RoleModel roleModel);

}
