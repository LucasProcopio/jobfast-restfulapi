package com.lhpdesenvolvimentos.jobfast.job.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient himalayasRestClient() {
        return RestClient.builder()
                .baseUrl("https://himalayas.app")
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
