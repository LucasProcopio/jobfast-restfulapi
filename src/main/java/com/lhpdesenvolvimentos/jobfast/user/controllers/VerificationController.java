package com.lhpdesenvolvimentos.jobfast.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lhpdesenvolvimentos.jobfast.user.application.dto.VerificationResult;
import com.lhpdesenvolvimentos.jobfast.user.application.service.VerificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Verification", description = "Endpoints para verificação de email")
public class VerificationController {
    private final VerificationService verificationService;

    @GetMapping("/v1/api/verify")
    @Operation(summary = "Verificar email", description = "Endpoint para verificar o email do usuário usando um token de verificação.", parameters = {
        @Parameter(name = "token", description = "Token de verificação enviado por email", required = true)
    })
    public ResponseEntity<VerificationResult> verifyEmail(@RequestParam("token") String token) {
        var result = verificationService.verifyToken(token);
        if (!result.success()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
