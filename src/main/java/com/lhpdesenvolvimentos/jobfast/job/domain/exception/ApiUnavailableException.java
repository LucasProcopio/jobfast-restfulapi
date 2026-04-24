package com.lhpdesenvolvimentos.jobfast.job.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "External API is currently unavailable. Please try again later.")
public class ApiUnavailableException extends DomainException {
    public ApiUnavailableException(String message) {
        super(message);
    }
}
