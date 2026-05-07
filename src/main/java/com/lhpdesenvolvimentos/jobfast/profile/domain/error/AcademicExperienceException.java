package com.lhpdesenvolvimentos.jobfast.profile.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lhpdesenvolvimentos.jobfast.job.domain.exception.DomainException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Error related to the 'Academic Experience' section. Please check the details and try again.")
public class AcademicExperienceException extends DomainException {
    public AcademicExperienceException(String message) {
        super(message);
    }
}
