package com.smartpark.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey signingKey;
    private final String issuer;
    private final long expirationMillis;

    public JwtService(
            @Value("${smartpark.jwt.secret-base64}") String secretBase64,
            @Value("${smartpark.jwt.issuer}") String issuer,
            @Value("${smartpark.jwt.expiration-minutes:15}") long expirationMinutes) {
        this.issuer = issuer;
        this.expirationMillis = expirationMinutes * 60_000L;

        if (secretBase64 == null
                || secretBase64.startsWith("REPLACE_WITH_")
                || secretBase64.isBlank()) {
            throw new IllegalStateException(
                    "Set SMARTPARK_JWT_SECRET to a Base64-encoded secret of at least 32 bytes. "
                    + "See README.md.");
        }

        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(secretBase64);
        } catch (RuntimeException ex) {
            throw new IllegalStateException("SMARTPARK_JWT_SECRET must be valid Base64", ex);
        }

        if (keyBytes.length < 32) {
            throw new IllegalStateException("JWT secret must decode to at least 32 bytes");
        }
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .issuer(issuer)
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMillis)))
                .claim("roles", "USER")
                .signWith(signingKey)
                .compact();
    }

    public Claims validateAndParse(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public long getExpirationSeconds() {
        return expirationMillis / 1000L;
    }
}
