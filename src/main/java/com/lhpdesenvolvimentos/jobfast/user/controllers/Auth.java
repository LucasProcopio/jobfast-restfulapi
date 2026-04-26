package com.lhpdesenvolvimentos.jobfast.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lhpdesenvolvimentos.jobfast.user.application.dto.AuthRequest;
import com.lhpdesenvolvimentos.jobfast.user.application.dto.AuthResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Authentication", description = "Endpoints for user authentication and token management")
public class Auth {
    
    @PostMapping("/v1/api/auth/login")
    @Operation(summary = "Authenticate user and return access token", description = "Validates user credentials and returns an access token along with refresh token and expiration time.")
    public ResponseEntity<AuthResponse> authUser(@Valid @RequestBody AuthRequest req) {
        // TODO: Implement authentication logic (validate credentials, generate token, etc.)
        // For now, return a dummy response
        AuthResponse response = new AuthResponse("dummy-token", "accesstoken", "refreshtoken", 3600L);
        return ResponseEntity.ok(response);
    } 
}
