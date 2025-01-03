package com.user_microservice.user.infrastructure.security.service;

import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.util.Util;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    public String generateToken(UserModel user, Map<String, Object> extraClaims) {
        logger.info("[Infraestructura] [JwtService] Generando token JWT.");
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + Util.TOKEN_EXPIRATION_TIME);
        String token = Jwts.builder().setClaims(extraClaims)
                .setSubject(user.getId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
        logger.info("[Infraestructura] [JwtService] Token JWT generado exitosamente.");
        return token;
    }

    private Key generateKey() {
        logger.info("[Infraestructura] [JwtService] Generando clave secreta para firmar el token.");
        byte[] secretAsBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    public String extractUsername(String jwt) {
        logger.info("[Infraestructura] [JwtService] Extrayendo el nombre de usuario del token JWT.");
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        logger.info("[Infraestructura] [JwtService] Extrayendo todos los claims del token JWT.");
        return Jwts.parserBuilder().setSigningKey(generateKey()).build()
                .parseClaimsJws(jwt).getBody();
    }

    public boolean isTokenValid(String token) {
        logger.info("[Infraestructura] [JwtService] Validando el token JWT.");
        try {
            Claims claims = extractAllClaims(token);
            boolean isValid = !isTokenExpired(claims);
            logger.info("[Infraestructura] [JwtService] Token JWT validado exitosamente.");
            return isValid;
        } catch (JwtException | IllegalArgumentException e) {
            logger.warn("[Infraestructura] [JwtService] Token JWT inválido.", e);
            return false;
        }
    }

    private Boolean isTokenExpired(Claims claims) {
        logger.info("[Infraestructura] [JwtService] Verificando si el token JWT está expirado.");
        return claims.getExpiration().before(new Date());
    }
}
