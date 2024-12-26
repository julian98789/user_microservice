package com.user_microservice.user.infrastructure.output.jpa.repository;

import com.user_microservice.user.infrastructure.output.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository <RoleEntity,Long> {

}
