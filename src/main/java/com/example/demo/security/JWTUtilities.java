package com.example.demo.security;

import com.example.demo.dto.user.UserDTO;
import com.example.demo.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JWTUtilities {

    private static final String KEY = "rhx4hCGFr0MBI5geEwvfvHb8dt7PZeTt23j1jaJjd18283jHHasd71278hd7Hghaghe1828hdaghs7d2892j";

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDTO user) {
        long today = System.currentTimeMillis();
        long expirationTime = today + 1000L * 60 * 60 * 24 * 30;

        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(today))
                .expiration(new Date (expirationTime))
                .claims()
                .add("role", user.getRole().toString())
                .and()
                .signWith(getSecretKey())
                .compact();

    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token) {
        return parseToken(token).getSubject();
    }

    public LocalDateTime getIssuedAt(String token) {
        Date issuedAtDate = parseToken(token).getIssuedAt();
        return LocalDateTime.from(issuedAtDate.toInstant());
    }
    public LocalDateTime getExpiration(String token) {
        Date expirationDate = parseToken(token).getExpiration();
        return LocalDateTime.from(expirationDate.toInstant());
    }
    public Role getRole(String token) {
        String role = parseToken(token).get("role", String.class);
        return Role.valueOf(role);
    }
}
