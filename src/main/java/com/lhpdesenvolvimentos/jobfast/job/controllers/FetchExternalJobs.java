package com.lhpdesenvolvimentos.jobfast.job.controllers;

import com.lhpdesenvolvimentos.jobfast.job.application.dto.HimalayasResponse;
import com.lhpdesenvolvimentos.jobfast.job.application.services.FindJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "External Jobs", description = "Fetch jobs from external sources")
public class FetchExternalJobs {
    private final FindJobService findJobService;

    public FetchExternalJobs(FindJobService findJobService) {
        this.findJobService = findJobService;
    }

    @GetMapping("/fetch-external-jobs")
    @Operation(
        summary = "Fetch external jobs",
        description = "Fetches job listings from the Himalayas API"
    )
    public HimalayasResponse fetchExternalJobs(
            @Parameter(description = "Number of jobs to fetch", example = "10")
            @RequestParam(defaultValue = "10") int limit) {
        return findJobService.fetchJobs(limit);
    }
}
