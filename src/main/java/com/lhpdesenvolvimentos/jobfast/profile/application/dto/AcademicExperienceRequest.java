package com.lhpdesenvolvimentos.jobfast.profile.application.dto;

import java.time.LocalDate;

import com.lhpdesenvolvimentos.jobfast.profile.domain.model.AcedemicExperienceEntity.AcademicQualification;
import com.lhpdesenvolvimentos.jobfast.profile.domain.model.AcedemicExperienceEntity.AcademicStatus;
import com.lhpdesenvolvimentos.jobfast.profile.domain.model.AcedemicExperienceEntity.EducationLevel;
import com.lhpdesenvolvimentos.jobfast.profile.domain.validation.ValidDateRange;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@ValidDateRange(allowNullEnd = true, message = "endDate must be after or equal to startDate, or null if ongoing")
public record AcademicExperienceRequest(
        @NotBlank(message = "Institution name is required") String institution,
        @NotBlank(message = "Course name is required") String course,
        @NotNull(message = "Academic status is required") AcademicStatus status,
        @NotNull(message = "Academic qualification is required") AcademicQualification academicQualification,
        @NotNull(message = "Educational level is required") EducationLevel educationLevel,
        @NotNull(message = "Start date is required") @PastOrPresent(message = "Start date must not be in the future") LocalDate startDate,
        LocalDate endDate) {

}
