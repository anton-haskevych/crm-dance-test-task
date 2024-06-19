package com.example.demo.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.security.user.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expiration;
    private static final String TOKEN_PREFIX = "Bearer ";


    public String generateToken(JwtUser user, String companyId) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("companyId", companyId)
                .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault()).toInstant().toEpochMilli() + Long.parseLong(expiration)))
                .sign(Algorithm.HMAC256(secret));
    }
}
