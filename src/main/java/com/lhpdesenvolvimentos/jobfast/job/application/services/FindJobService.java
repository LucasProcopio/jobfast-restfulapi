package com.lhpdesenvolvimentos.jobfast.job.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lhpdesenvolvimentos.jobfast.job.application.dto.HimalayasResponse;
import com.lhpdesenvolvimentos.jobfast.job.application.mapper.JobMapper;
import com.lhpdesenvolvimentos.jobfast.job.domain.model.Job;
import com.lhpdesenvolvimentos.jobfast.job.domain.repository.JobRepository;
import com.lhpdesenvolvimentos.jobfast.job.infrastructure.adapters.HimalayasClient;

@Service
public class FindJobService {

    private final JobMapper jobMapper;
    private final JobRepository jobRepository;
    private final HimalayasClient himalayasClient;

    public FindJobService(HimalayasClient himalayasClient, JobMapper jobMapper, JobRepository jobRepository) {
        this.himalayasClient = himalayasClient;
        this.jobMapper = jobMapper;
        this.jobRepository = jobRepository;
    }

    public HimalayasResponse fetchJobs(int limit) {

        HimalayasResponse himalayasResponse =  himalayasClient.callHimalayasApi(limit);

        List<Job> jobs = jobMapper.toJobEntityList(himalayasResponse.jobs());
        jobRepository.saveAllAndFlush(jobs);

        return himalayasResponse;
    }
}
