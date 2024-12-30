package com.user_microservice.user.domain.usecase;

import com.user_microservice.user.domain.exception.RoleNameNotFoundException;
import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.model.RoleName;
import com.user_microservice.user.domain.spi.IRoleModelPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleModelUseCaseTest {


    @Mock
    private IRoleModelPersistencePort roleModelPersistencePort;

    @InjectMocks
    private RoleModelUseCase roleModelUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Verificar si existe un rol por nombre válido debe devolver verdadero")
    void existsRoleByNameCase1() {
        String roleName = "ADMIN";
        RoleModel roleModel = new RoleModel(1L, RoleName.ADMIN, "Admin");

        when(roleModelPersistencePort.getRoleByName(RoleName.ADMIN)).thenReturn(roleModel);

        boolean result = roleModelUseCase.existsRoleByName(roleName);

        assertTrue(result);
        verify(roleModelPersistencePort, times(1)).getRoleByName(RoleName.ADMIN);
    }

    @Test
    @DisplayName("Verificar si existe un rol por nombre inválido debe lanzar RoleNameNotFoundException")
    void existsRoleByNameCase2() {
        String roleName = "INVALID_ROLE";

        assertThrows(RoleNameNotFoundException.class, () -> roleModelUseCase.existsRoleByName(roleName));
        verify(roleModelPersistencePort, never()).getRoleByName(any());
    }
}