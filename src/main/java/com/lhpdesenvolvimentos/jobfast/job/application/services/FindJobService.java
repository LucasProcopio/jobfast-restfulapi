package com.lhpdesenvolvimentos.jobfast.job.application.services;

import com.lhpdesenvolvimentos.jobfast.job.application.dto.HimalayasResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class FindJobService {

    private final RestClient himalayasRestClient;

    public FindJobService(RestClient himalayasRestClient) {
        this.himalayasRestClient = himalayasRestClient;
    }

    public HimalayasResponse fetchJobs(int limit) {
        return himalayasRestClient.get()
                .uri("/jobs/api?limit=" + limit)
                .retrieve()
                .body(HimalayasResponse.class);
    }
}
