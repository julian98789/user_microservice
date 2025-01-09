package com.user_microservice.user.infrastructure.persistence.jpa.adapter;

import com.user_microservice.user.domain.exception.RoleNameNotFoundException;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.util.RoleName;
import com.user_microservice.user.infrastructure.persistence.jpa.entity.RoleEntity;
import com.user_microservice.user.infrastructure.persistence.jpa.mapper.IRoleEntityMapper;
import com.user_microservice.user.infrastructure.persistence.jpa.repository.IRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

class RoleJpaAdapterTest {


    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private IRoleEntityMapper roleEntityMapper;

    @InjectMocks
    private RoleJpaAdapter roleJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Obtener el rol por nombre debe devolver RoleModel cuando el rol existe")
    void getRoleByNameCase1() {
        RoleName roleName = RoleName.ADMIN;
        RoleEntity roleEntity = new RoleEntity();
        RoleModel roleModel = new RoleModel(1L, roleName, "Admin");

        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(roleEntity));
        when(roleEntityMapper.roleEntityToRoleModel(roleEntity)).thenReturn(roleModel);

        RoleModel result = roleJpaAdapter.getRoleByName(roleName);

        assertNotNull(result);
        assertEquals(roleModel, result);
        verify(roleRepository, times(1)).findByName(roleName);
        verify(roleEntityMapper, times(1)).roleEntityToRoleModel(roleEntity);
    }

    @Test
    @DisplayName("Obtener un rol por nombre debería generar una RoleNameNotFoundException cuando el rol no existe")
    void getRoleByNameCase2() {
        RoleName roleName = RoleName.ADMIN;

        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        assertThrows(RoleNameNotFoundException.class, () -> roleJpaAdapter.getRoleByName(roleName));
        verify(roleRepository, times(1)).findByName(roleName);
        verify(roleEntityMapper, never()).roleEntityToRoleModel(any());
    }
}