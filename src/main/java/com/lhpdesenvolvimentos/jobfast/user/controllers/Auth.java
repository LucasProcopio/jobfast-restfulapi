package com.lhpdesenvolvimentos.jobfast.user.controllers;

import org.hibernate.mapping.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lhpdesenvolvimentos.jobfast.user.application.dto.AuthRequest;
import com.lhpdesenvolvimentos.jobfast.user.application.dto.AuthResponse;
import com.lhpdesenvolvimentos.jobfast.user.application.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Authentication", description = "Endpoints for user authentication and token management")
public class Auth {
    private final AuthService authService;

    public Auth(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/v1/api/auth/login")
    @Operation(summary = "Authenticate user and return access token", description = "Validates user credentials and returns an access token along with refresh token and expiration time.")
    public ResponseEntity<AuthResponse> authUser(@Valid @RequestBody AuthRequest req) throws AuthException {
        AuthResponse response = authService.authenticate(req.email(), req.password());
        return ResponseEntity.ok(response);
    }
}
