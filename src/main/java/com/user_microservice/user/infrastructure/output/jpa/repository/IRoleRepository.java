package com.user_microservice.user.infrastructure.output.jpa.repository;

import com.user_microservice.user.domain.model.RoleName;
import com.user_microservice.user.infrastructure.output.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository <RoleEntity,Long> {

    Optional<RoleEntity> findByName(RoleName name);

}
