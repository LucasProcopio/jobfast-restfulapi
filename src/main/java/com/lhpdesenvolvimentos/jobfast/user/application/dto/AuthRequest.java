package com.lhpdesenvolvimentos.jobfast.user.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthRequest(
        String email,
        String password
) {
}
