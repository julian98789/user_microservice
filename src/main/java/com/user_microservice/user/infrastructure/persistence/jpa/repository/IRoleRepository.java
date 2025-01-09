package com.user_microservice.user.infrastructure.persistence.jpa.repository;

import com.user_microservice.user.domain.util.RoleName;
import com.user_microservice.user.infrastructure.persistence.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository <RoleEntity,Long> {

    Optional<RoleEntity> findByName(RoleName name);

}
