package com.lhpdesenvolvimentos.jobfast.job.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI jobfastOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Jobfast API")
                        .description("Job aggregator and search platform")
                        .version("1.0.0"));
    }
}
