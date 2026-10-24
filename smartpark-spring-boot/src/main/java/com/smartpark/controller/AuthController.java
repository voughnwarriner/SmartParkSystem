package com.smartpark.controller;

import com.smartpark.dto.LoginRequest;
import com.smartpark.dto.TokenResponse;
import com.smartpark.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        String token = authenticationService.authenticateAndGenerateToken(
                request.username(), request.password());
        return new TokenResponse(token, "Bearer",
                authenticationService.getTokenLifetimeSeconds());
    }
}
