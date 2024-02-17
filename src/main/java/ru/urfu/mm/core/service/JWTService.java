package ru.urfu.mm.core.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.Duration;
import java.util.*;


@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    public String generateToken(UserDetails userDetails) {
        Date issuedDate = new Date();
        Date expireDate;
        if(jwtLifetime == null) {
            expireDate = new Date(issuedDate.getTime() + 10);
        } else {
            expireDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());
        }

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expireDate)
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(String token) {
        List<String> claims = getAllClaimsFromToken(token).get("roles", List.class);
        if(claims == null) {
            claims = Collections.emptyList();
        }
        return claims;
    }
}