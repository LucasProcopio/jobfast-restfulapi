package com.lhpdesenvolvimentos.jobfast.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhpdesenvolvimentos.jobfast.user.application.dto.RegistrationResponse;
import com.lhpdesenvolvimentos.jobfast.user.application.dto.UserRegistrationRequest;
import com.lhpdesenvolvimentos.jobfast.user.application.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/v1/public/api/register")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class Register {
    private final UserService userService;

    public Register(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    @Operation(
        summary = "Register a new user",
        description = "Registers a new user with the provided email and password"
    )
    public ResponseEntity<RegistrationResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        userService.register(request);
        return  ResponseEntity.status(HttpStatus.CREATED).body(new RegistrationResponse("User registered successfully."));
    }
}
