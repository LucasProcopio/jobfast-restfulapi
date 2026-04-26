package com.lhpdesenvolvimentos.jobfast.job.infrastructure.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import com.lhpdesenvolvimentos.jobfast.job.application.dto.HimalayasResponse;
import com.lhpdesenvolvimentos.jobfast.job.domain.exception.ApiUnavailableException;
import com.lhpdesenvolvimentos.jobfast.job.domain.exception.NoJobException;

@Component
public class HimalayasClient {
    private final RestClient himalayasRestClient;
    private final Logger log = LoggerFactory.getLogger(HimalayasClient.class);

    public HimalayasClient(RestClient himalayasRestClient) {
        this.himalayasRestClient = himalayasRestClient;
    }

    public HimalayasResponse callHimalayasApi(int limit) {
        log.info("Calling Himalayas API with limit {}", limit);
        try {
            return  himalayasRestClient.get()
                    .uri("/jobs/api?limit=" + limit)
                    .retrieve()
                    .body(HimalayasResponse.class);
        } catch (RestClientResponseException ex) {
            int status = ex.getStatusCode().value();
            log.warn("Himalayas API returned status {}", status);
            if(status == 404) {
                throw new NoJobException("No jobs found in the Himalayas API response");
            } else {
                throw new ApiUnavailableException("No response received from the Himalayas API");
            }
        } catch (RestClientException ex) {
            log.error("Error while calling the Himalayas API: /jobs/api?limit={}, limit, error {}", limit, ex.getMessage(), ex);
            throw new ApiUnavailableException("Error while calling the Himalayas API: " + ex.getMessage());
        }
    }

    public HimalayasResponse callHimalayasApiWithOffset(int limit, int offset) {
        try {
            return  himalayasRestClient.get()
                    .uri("/jobs/api?offset=" + offset + "&limit=" + limit)
                    .retrieve()
                    .body(HimalayasResponse.class);
        } catch (RestClientResponseException ex) {
            int status = ex.getStatusCode().value();
            log.warn("Himalayas job with offset API returned status {}", status);
            if(status == 404) {
                throw new NoJobException("No jobs found in the Himalayas API response");
            } else {
                throw new ApiUnavailableException("No response received from the Himalayas API");
            }
        } catch (RestClientException ex) {
            log.error("Error while calling the Himalayas API: /jobs/api?limit={}, limit, error {}", limit, ex.getMessage(), ex);
            throw new ApiUnavailableException("Error while calling the Himalayas API: " + ex.getMessage());
        }
    }
}
