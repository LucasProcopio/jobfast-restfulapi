package com.lhpdesenvolvimentos.jobfast.profile.controllers;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AboutRequest;
import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AboutResponse;
import com.lhpdesenvolvimentos.jobfast.profile.application.service.AboutService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/v1/api/profile")
@Tag(name = "Profile", description = "Endpoints related to user profile management")
public class AboutController {
    private final AboutService aboutService;

    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }
    
    @PostMapping("/about")
    @Operation(summary="User about information", description="Update user profile about information")
    public ResponseEntity<AboutResponse> about(@Valid @RequestBody AboutRequest req) {
        AboutResponse response = aboutService.updateAbout(req);
        return ResponseEntity.ok().body(response);
    }
    
}
