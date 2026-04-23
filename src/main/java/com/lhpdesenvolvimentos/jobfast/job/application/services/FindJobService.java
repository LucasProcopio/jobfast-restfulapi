package com.lhpdesenvolvimentos.jobfast.job.application.services;

import com.lhpdesenvolvimentos.jobfast.job.application.dto.HimalayasResponse;
import com.lhpdesenvolvimentos.jobfast.job.application.mapper.JobMapper;
import com.lhpdesenvolvimentos.jobfast.job.domain.exception.ApiUnavailableException;
import com.lhpdesenvolvimentos.jobfast.job.domain.exception.NoJobException;
import com.lhpdesenvolvimentos.jobfast.job.domain.model.Job;
import com.lhpdesenvolvimentos.jobfast.job.domain.repository.JobRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class FindJobService {

    private final RestClient himalayasRestClient;
    private final JobMapper jobMapper;
    private final JobRepository jobRepository;

    public FindJobService(RestClient himalayasRestClient, JobMapper jobMapper, JobRepository jobRepository) {
        this.himalayasRestClient = himalayasRestClient;
        this.jobMapper = jobMapper;
        this.jobRepository = jobRepository;
    }

    public HimalayasResponse fetchJobs(int limit) {
        HimalayasResponse himalayasResponse =  himalayasRestClient.get()
                .uri("/jobs/api?limit=" + limit)
                .retrieve()
                .body(HimalayasResponse.class);

        if(himalayasResponse == null ) {
            throw new ApiUnavailableException("No response received from the Himalayas API");
        }

        if(himalayasResponse.jobs() == null || himalayasResponse.jobs().isEmpty()) {
            throw new RuntimeException("No jobs found in the Himalayas API response");
        }

        List<Job> jobs = jobMapper.toJobEntityList(himalayasResponse.jobs());
        jobRepository.saveAllAndFlush(jobs);

        return himalayasResponse;
    }
}
