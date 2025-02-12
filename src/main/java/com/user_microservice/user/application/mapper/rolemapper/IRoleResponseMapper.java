package com.user_microservice.user.application.mapper.rolemapper;

import com.user_microservice.user.application.dto.roledto.RoleResponse;
import com.user_microservice.user.domain.model.RoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IRoleResponseMapper {

    RoleResponse roleModelToRoleResponse(RoleModel roleModel);

}
