package com.lhpdesenvolvimentos.jobfast.job.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lhpdesenvolvimentos.jobfast.job.application.dto.HimalayasResponse;
import com.lhpdesenvolvimentos.jobfast.job.application.services.FindJobService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/public/api/jobs")
@Tag(name = "External Jobs", description = "Fetch jobs from external sources")
public class FetchExternalJobs {
    private final FindJobService findJobService;

    public FetchExternalJobs(FindJobService findJobService) {
        this.findJobService = findJobService;
    }

    @GetMapping("/")
    @Operation(
        summary = "Fetch external jobs",
        description = "Fetches job listings from the Himalayas API"
    )
    public HimalayasResponse fetchExternalJobs(
            @Parameter(description = "Number of jobs to fetch", example = "10")
            @RequestParam(name = "limit",defaultValue = "10") int limit) {
        return findJobService.fetchJobs(limit);
    }
}
