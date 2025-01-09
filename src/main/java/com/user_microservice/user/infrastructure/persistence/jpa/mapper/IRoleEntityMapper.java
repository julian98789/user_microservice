package com.user_microservice.user.infrastructure.persistence.jpa.mapper;


import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.infrastructure.persistence.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleEntityMapper {

    RoleModel roleEntityToRoleModel(RoleEntity roleEntity);

}
