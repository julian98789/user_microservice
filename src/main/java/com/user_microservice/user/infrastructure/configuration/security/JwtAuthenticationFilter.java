package com.user_microservice.user.infrastructure.configuration.security;

import com.user_microservice.user.infrastructure.output.jpa.entity.UserEntity;
import com.user_microservice.user.infrastructure.output.jpa.repository.IUserRepository;
import com.user_microservice.user.infrastructure.security.service.JwtService;
import com.user_microservice.user.domain.util.Util;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger jwtAuthenticationLogger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtAuthenticationLogger.info("[Infraestructura] [JwtAuthenticationFilter] Iniciando filtro JWT.");

        String authHeader = request.getHeader(Util.AUTH_HEADER);

        if (authHeader == null || !authHeader.startsWith(Util.TOKEN_PREFIX)) {
            jwtAuthenticationLogger.warn("[Infraestructura] [JwtAuthenticationFilter] Encabezado de autorización no encontrado o inválido.");
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(Util.TOKEN_PREFIX_LENGTH);
        try {
            jwtAuthenticationLogger.info("[Infraestructura] [JwtAuthenticationFilter] Validando token JWT.");
            if (!jwtService.isTokenValid(jwt)) {
                jwtAuthenticationLogger.warn("[Infraestructura] [JwtAuthenticationFilter] Token JWT inválido.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Util.INVALID_TOKEN);
                return;
            }

            String userName = jwtService.extractUsername(jwt);
            jwtAuthenticationLogger.info("[Infraestructura] [JwtAuthenticationFilter] Token JWT válido. Extrayendo usuario con ID: {}.", userName);

            UserEntity user = userRepository.findById(Long.parseLong(userName))
                    .orElseThrow(() -> new RuntimeException(Util.USER_NOT_FOUND));


            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userName, null, user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
            jwtAuthenticationLogger.info("[Infraestructura] [JwtAuthenticationFilter] Usuario autenticado exitosamente.");

        } catch (ExpiredJwtException e) {
            jwtAuthenticationLogger.error("[Infraestructura] [JwtAuthenticationFilter] Token JWT expirado.", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Util.TOKEN_EXPIRED);
            return;
        } catch (Exception e) {
            jwtAuthenticationLogger.error("[Infraestructura] [JwtAuthenticationFilter] Error durante la validación del token JWT.", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Util.INVALID_TOKEN);
            return;
        }

        jwtAuthenticationLogger.info("[Infraestructura] [JwtAuthenticationFilter] Filtro JWT completado exitosamente.");
        filterChain.doFilter(request, response);
    }
}
