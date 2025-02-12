package com.user_microservice.user.infrastructure.security.service;

import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.domain.util.Util;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {


    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    public String generateToken(UserModel user, Map<String, Object> extraClaims) {

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Util.TOKEN_EXPIRATION_TIME))
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key generateKey() {
        byte[] secretAsBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build()
                .parseClaimsJws(jwt).getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !isTokenExpired(claims);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
