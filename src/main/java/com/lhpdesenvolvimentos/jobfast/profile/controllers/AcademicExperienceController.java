package com.lhpdesenvolvimentos.jobfast.profile.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AcademicExperienceRequest;
import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AcademicExperienceResponse;
import com.lhpdesenvolvimentos.jobfast.profile.application.service.AcademicExperienceService;
import com.lhpdesenvolvimentos.jobfast.profile.domain.error.AcademicExperienceException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/api/profile")
@Tag(name = "Profile", description = "Endpoints related to user profile management")
@Slf4j
@Validated
public class AcademicExperienceController {
    private final AcademicExperienceService academicExperienceService;

    public AcademicExperienceController(AcademicExperienceService academicExperienceService) {
        this.academicExperienceService = academicExperienceService;
    }

    @PostMapping("academic-experience")
    @Operation(summary = "User academic experience", description = "Update user academic experience", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<AcademicExperienceResponse> postMethodName(
            @AuthenticationPrincipal(expression = "claims['sub']") String userId,
            @RequestBody @Valid List<@Valid AcademicExperienceRequest> request) throws AcademicExperienceException {
        AcademicExperienceResponse response = academicExperienceService.updateAcademicExperience(userId, request);
        return ResponseEntity.ok().body(response);
    }

}
