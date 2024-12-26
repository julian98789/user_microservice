package com.user_microservice.user.infrastructure.output.jpa.mapper;


import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.infrastructure.output.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleEntityMapper {

    RoleEntity roleModelToRoleEntity(RoleModel roleModel);
    RoleModel roleEntityToRoleModel(RoleEntity roleEntity);

}
