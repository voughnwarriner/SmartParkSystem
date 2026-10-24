package com.smartpark.service;

import com.smartpark.exception.UnauthorizedException;
import com.smartpark.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final String configuredUsername;
    private final String configuredPassword;
    private final JwtService jwtService;

    public AuthenticationService(
            @Value("${smartpark.auth.username}") String configuredUsername,
            @Value("${smartpark.auth.password}") String configuredPassword,
            JwtService jwtService) {
        this.configuredUsername = configuredUsername;
        this.configuredPassword = configuredPassword;
        this.jwtService = jwtService;
    }

    public String authenticateAndGenerateToken(String username, String password) {
        if (!configuredUsername.equals(username) || !configuredPassword.equals(password)) {
            throw new UnauthorizedException("Invalid username or password");
        }
        return jwtService.generateToken(username);
    }

    public long getTokenLifetimeSeconds() {
        return jwtService.getExpirationSeconds();
    }
}
