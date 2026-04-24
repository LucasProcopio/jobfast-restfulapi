package com.lhpdesenvolvimentos.jobfast.job.application.services;

import com.lhpdesenvolvimentos.jobfast.job.application.dto.HimalayasResponse;
import com.lhpdesenvolvimentos.jobfast.job.application.mapper.JobMapper;
import com.lhpdesenvolvimentos.jobfast.job.domain.exception.ApiUnavailableException;
import com.lhpdesenvolvimentos.jobfast.job.domain.exception.NoJobException;
import com.lhpdesenvolvimentos.jobfast.job.domain.model.Job;
import com.lhpdesenvolvimentos.jobfast.job.domain.repository.JobRepository;
import com.lhpdesenvolvimentos.jobfast.job.infrastructure.adapters.HimalayasClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

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
