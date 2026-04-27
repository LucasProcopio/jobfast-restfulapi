package com.lhpdesenvolvimentos.jobfast.user.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lhpdesenvolvimentos.jobfast.job.domain.exception.DomainException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User verification failed. Please check the token and try again.")
public class UserVerificationException extends DomainException {
    public UserVerificationException(String message) {
        super(message);
    }
    
}
