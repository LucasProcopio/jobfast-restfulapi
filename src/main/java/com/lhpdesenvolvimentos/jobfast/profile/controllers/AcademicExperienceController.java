package com.lhpdesenvolvimentos.jobfast.profile.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AcademicExperienceRequest;
import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AcademicExperienceResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/api/profile")
@Tag(name = "Profile", description = "Endpoints related to user profile management")
@Slf4j
public class AcademicExperienceController {

    @PostMapping("academic-experience")
    @Operation(summary = "User academic experience", description = "Update user academic experience", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<AcademicExperienceResponse> postMethodName(
            @AuthenticationPrincipal(expression = "claims['sub']") String userId,
            @Valid @RequestBody List<AcademicExperienceRequest> request) {
        // Add exception handlers for validations
        // TODO: process POST request

        return ResponseEntity.ok(new AcademicExperienceResponse("Academic experience created successfully"));
    }

}
