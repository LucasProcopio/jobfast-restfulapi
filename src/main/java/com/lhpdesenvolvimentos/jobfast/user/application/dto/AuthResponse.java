package com.lhpdesenvolvimentos.jobfast.user.application.dto;

public record AuthResponse(
                String accessToken,
                String refreshToken,
                long expiresIn) {
}
