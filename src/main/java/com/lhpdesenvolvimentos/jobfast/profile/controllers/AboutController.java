package com.lhpdesenvolvimentos.jobfast.profile.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AboutRequest;
import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AboutResponse;
import com.lhpdesenvolvimentos.jobfast.profile.application.service.AboutService;
import com.lhpdesenvolvimentos.jobfast.profile.domain.error.AboutException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/v1/api/profile")
@Tag(name = "Profile", description = "Endpoints related to user profile management")
@Slf4j
public class AboutController {
    private final AboutService aboutService;

    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }
    
    @PostMapping("/about")
    @Operation(summary="User about information", description="Update user profile about information", security= @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<AboutResponse> about(
        @AuthenticationPrincipal(expression = "claims['sub']") String userId,
        @Valid
        @RequestBody AboutRequest req) throws AboutException {
        log.info("Received about update request for userId: {}", userId);
        AboutResponse response = aboutService.updateAbout(userId, req);
        return ResponseEntity.ok().body(response);
    }
    
}
