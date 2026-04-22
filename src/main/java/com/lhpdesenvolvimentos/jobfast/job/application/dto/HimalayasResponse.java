package com.lhpdesenvolvimentos.jobfast.job.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HimalayasResponse(
        String comments,
        Long updatedAt,
        Integer offset,
        Integer limit,
        Integer totalCount,
        List<HimalayasJob> jobs
) {
}
