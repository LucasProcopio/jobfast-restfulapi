package com.lhpdesenvolvimentos.jobfast.job.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No job found with the given criteria.")
public class NoJobException extends DomainException {
        public NoJobException(String message) {
            super(message);
        }
}
