package com.lhpdesenvolvimentos.jobfast.job.application.dto;

import java.time.Instant;

public record ErrorResponse(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
