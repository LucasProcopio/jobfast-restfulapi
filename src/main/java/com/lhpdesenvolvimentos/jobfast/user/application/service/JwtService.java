package com.lhpdesenvolvimentos.jobfast.user.application.service;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final String issuer;
    private final long accessTokenMillis;
    private final long refreshTokenMillis;

    public JwtService(PrivateKey privateKey, PublicKey publicKey,
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.access-token.expiration-minutes}") long accessMinutes,
            @Value("${jwt.refresh-token.expiration-days}") long refreshDays) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.issuer = issuer;
        this.accessTokenMillis = accessMinutes * 60 * 1000;
        this.refreshTokenMillis = refreshDays * 24 * 60 * 60 * 1000;
    }

    public String generateAccessToken(String subject) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(accessTokenMillis)))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public String generateRefreshToken(String subject) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(refreshTokenMillis)))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public Claims validateAndParse(String token) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);
            // signature valid and token parsed
            Claims claims = jws.getBody();
            if (claims.getExpiration() != null && claims.getExpiration().before(new Date())) {
                throw new JwtException("Token expired");
            }
            return claims;
        } catch (JwtException ex) {
            throw new RuntimeException("Invalid JWT", ex);
        }
    }
}
