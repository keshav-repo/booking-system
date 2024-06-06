package com.example.bookingservice.helper;

import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Component
public class JwtHelper {
    @Value("${jwt.expiry.secret}")
    private byte[] secret;
    @Value("${jwt.expiry.timeInMin}")
    private String expiryInMin;

    public String generateToken(String userName) {
        SecretKey secretKey = new SecretKeySpec(secret,  "HmacSHA256");
        var now = Instant.now();
        return Jwts.builder()
                .subject(userName)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(Long.valueOf(expiryInMin), ChronoUnit.MINUTES)))
                .signWith(secretKey)
                .compact();
    }

    

}
