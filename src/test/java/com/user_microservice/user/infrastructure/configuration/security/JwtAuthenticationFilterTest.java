package com.user_microservice.user.infrastructure.configuration.security;

import com.user_microservice.user.domain.util.RoleName;
import com.user_microservice.user.domain.util.Util;
import com.user_microservice.user.infrastructure.output.jpa.entity.RoleEntity;
import com.user_microservice.user.infrastructure.output.jpa.entity.UserEntity;
import com.user_microservice.user.infrastructure.output.jpa.repository.IUserRepository;
import com.user_microservice.user.infrastructure.security.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deberia autenticar al usuario cuando el token JWT es valido")
    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
        String token = "validToken";
        String userName = "1";
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(RoleName.ADMIN);
        userEntity.setRole(roleEntity);

        when(request.getHeader(Util.AUTH_HEADER)).thenReturn(Util.TOKEN_PREFIX + token);
        when(jwtService.isTokenValid(token)).thenReturn(true);
        when(jwtService.extractUsername(token)).thenReturn(userName);
        when(userRepository.findById(Long.parseLong(userName))).thenReturn(Optional.of(userEntity));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(userName, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    @DisplayName("No deberia autenticar al usuario cuando el token JWT es invalido")
    void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
        String token = "invalidToken";

        when(request.getHeader(Util.AUTH_HEADER)).thenReturn(Util.TOKEN_PREFIX + token);
        when(jwtService.isTokenValid(token)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, Util.INVALID_TOKEN);
        verify(filterChain, never()).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Should handle expired JWT token")
    void testDoFilterInternal_ExpiredToken() throws ServletException, IOException {
        String token = "expiredToken";

        when(request.getHeader(Util.AUTH_HEADER)).thenReturn(Util.TOKEN_PREFIX + token);
        when(jwtService.isTokenValid(token)).thenThrow(new ExpiredJwtException(null, null, "Token expired"));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, Util.TOKEN_EXPIRED);
        verify(filterChain, never()).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Deberia manejar la falta de encabezado de autorizacion")
    void testDoFilterInternal_MissingAuthHeader() throws ServletException, IOException {
        when(request.getHeader(Util.AUTH_HEADER)).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}