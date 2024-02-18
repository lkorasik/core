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

    public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parse(token);
    }

    public String generateToken(UserDetails userDetails) {
        Date issuedDate = new Date();
        Date expireDate;
        if(jwtLifetime == null) {
            expireDate = new Date(issuedDate.getTime() + 10);
        } else {
            expireDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());
        }

        Map<String, String> claims = new HashMap<>();
        claims.put("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name", userDetails.getUsername());
        claims.put("http://schemas.microsoft.com/ws/2008/06/identity/claims/role", "Admin"); // todo: Fix it
        claims.put("nbf", "1708189417");
        claims.put("exp", "1708275817");
        claims.put("iss", "MyAuthServer");
        claims.put("aud", "MyAuthClient");

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expireDate)
                .signWith(getSigningKey())
                .setClaims(claims)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        return (String) getAllClaimsFromToken(token).get("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name");
    }

    public List<String> getRoles(String token) {
        List<String> claims = getAllClaimsFromToken(token).get("roles", List.class);
        if(claims == null) {
            claims = Collections.emptyList();
        }
        return claims;
    }
}