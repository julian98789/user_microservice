package com.user_microservice.user.infrastructure.configuration.security;

import com.user_microservice.user.domain.util.RoleName;
import com.user_microservice.user.domain.util.Util;
import com.user_microservice.user.infrastructure.persistence.jpa.entity.RoleEntity;
import com.user_microservice.user.infrastructure.persistence.jpa.entity.UserEntity;
import com.user_microservice.user.infrastructure.persistence.jpa.repository.IUserRepository;
import com.user_microservice.user.infrastructure.security.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
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
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Given valid JWT token, when doFilterInternal, then authenticate user")
    void givenValidJwtToken_whenDoFilterInternal_thenAuthenticateUser() throws ServletException, IOException {
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
    @DisplayName("Given invalid JWT token, when doFilterInternal, then do not authenticate user")
    void givenInvalidJwtToken_whenDoFilterInternal_thenDoNotAuthenticateUser() throws ServletException, IOException {
        String token = "invalidToken";

        when(request.getHeader(Util.AUTH_HEADER)).thenReturn(Util.TOKEN_PREFIX + token);
        when(jwtService.isTokenValid(token)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, Util.INVALID_TOKEN);
        verify(filterChain, never()).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Given expired JWT token, when doFilterInternal, then handle expired token")
    void givenExpiredJwtToken_whenDoFilterInternal_thenHandleExpiredToken() throws ServletException, IOException {
        String token = "expiredToken";

        when(request.getHeader(Util.AUTH_HEADER)).thenReturn(Util.TOKEN_PREFIX + token);
        when(jwtService.isTokenValid(token)).thenThrow(new ExpiredJwtException(null, null, "Token expired"));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, Util.TOKEN_EXPIRED);
        verify(filterChain, never()).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Given missing authorization header, when doFilterInternal, then do not authenticate user")
    void givenMissingAuthHeader_whenDoFilterInternal_thenDoNotAuthenticateUser() throws ServletException, IOException {
        when(request.getHeader(Util.AUTH_HEADER)).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}