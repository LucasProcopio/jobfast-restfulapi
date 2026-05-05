package com.lhpdesenvolvimentos.jobfast.profile.domain.validation;

import java.time.LocalDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AcademicExperienceRequest;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, AcademicExperienceRequest> {
    private boolean allowNullEnd;

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        this.allowNullEnd = constraintAnnotation.allowNullEnd();
    }

    @Override
    public boolean isValid(AcademicExperienceRequest req, ConstraintValidatorContext ctx) {
        if (req == null)
            return true; // outra validação trata null request
        LocalDate start = req.startDate();
        LocalDate end = req.endDate();

        if (start == null)
            return true; // deixe @NotNull se startDate for obrigatório
        if (end == null)
            return allowNullEnd;
        return !end.isBefore(start); // end >= start
    }
}