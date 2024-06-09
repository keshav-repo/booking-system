package com.example.bookingservice.helper;

import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.security.CryptoPrimitive;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Component
public class JwtHelper {
    @Value("${jwt.expiry.secret}")
    private byte[] secret;
    @Value("${jwt.expiry.timeInMin}")
    private String expiryInMin;

    public String generateToken(String userName, List<String> roles) {
        SecretKey secretKey = new SecretKeySpec(secret, "HmacSHA256");
        var now = Instant.now();
        return Jwts.builder()
                .subject(userName)
                .issuedAt(Date.from(now))
                .claim("roles", roles)
                .expiration(Date.from(now.plus(Long.valueOf(expiryInMin), ChronoUnit.MINUTES)))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()); // && !isTokenExpired(token);
    }

    private Claims getTokenBody(String token) {
        SecretKey secretKey = new SecretKeySpec(secret, "HmacSHA256");
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SignatureException | ExpiredJwtException e) { // Invalid signature or expired token
            throw new AccessDeniedException("Access denied: " + e.getMessage());
        }
    }

}
