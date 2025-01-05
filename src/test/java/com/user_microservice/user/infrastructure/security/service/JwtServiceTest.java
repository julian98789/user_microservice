package com.user_microservice.user.infrastructure.security.service;

import com.user_microservice.user.domain.model.RoleModel;
import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.util.RoleName;
import com.user_microservice.user.domain.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private UserModel userModel;
    private Map<String, Object> extraClaims;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", "mySecretKey12345678901234567890123456789012");

        userModel = new UserModel(1L, new RoleModel(1L, RoleName.ADMIN, "admin"),
                "password", "test@example.com", LocalDate.of(2000, 1, 1),
                "123456789", "ID123", "Doe", "John");

        extraClaims = new HashMap<>();
        extraClaims.put("email", "test@example.com");
        extraClaims.put(Util.AUTHORITIES_KEY, "ROLE_ADMIN");
    }

    @Test
    @DisplayName("Debería generar un token JWT correctamente")
    void testGenerateToken() {
        String token = jwtService.generateToken(userModel, extraClaims);
        assertNotNull(token);
    }

    @Test
    @DisplayName("Debería extraer el nombre de usuario del token JWT")
    void testExtractUsername() {
        String token = jwtService.generateToken(userModel, extraClaims);
        String username = jwtService.extractUsername(token);
        assertEquals(userModel.getId().toString(), username);
    }

    @Test
    @DisplayName("Debería validar el token JWT correctamente")
    void testIsTokenValid() {
        String token = jwtService.generateToken(userModel, extraClaims);
        assertTrue(jwtService.isTokenValid(token));
    }


}