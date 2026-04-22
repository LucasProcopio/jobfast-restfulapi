package com.lhpdesenvolvimentos.jobfast.job.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HimalayasJob(
        String title,
        String description,
        Date pubDate,
        Date expiryDate,
        String companyName,
        String companySlug,
        String location,
        String[] seniority,
        String applicationLink,
        Integer maxSalary,
        Integer minSalary,
        String currency,
        String employmentType
) {
}
