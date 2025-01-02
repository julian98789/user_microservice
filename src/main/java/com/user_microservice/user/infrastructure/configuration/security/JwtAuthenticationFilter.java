package com.user_microservice.user.infrastructure.configuration.security;

import com.user_microservice.user.domain.util.Util;
import com.user_microservice.user.infrastructure.output.jpa.entity.UserEntity;
import com.user_microservice.user.infrastructure.output.jpa.repository.IUserRepository;
import com.user_microservice.user.infrastructure.security.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(Util.AUTH_HEADER);

        if (authHeader == null || !authHeader.startsWith(Util.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = authHeader.substring(Util.TOKEN_PREFIX_LENGTH);
        try {
            if (!jwtService.isTokenValid(jwt)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Util.INVALID_TOKEN);
                return;
            }
            String userName = jwtService.extractUsername(jwt);
            UserEntity user = userRepository.findById(Long.parseLong(userName))
                    .orElseThrow(() -> new RuntimeException(Util.USER_NOT_FOUND));
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Util.TOKEN_EXPIRED);
            return;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Util.INVALID_TOKEN);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
