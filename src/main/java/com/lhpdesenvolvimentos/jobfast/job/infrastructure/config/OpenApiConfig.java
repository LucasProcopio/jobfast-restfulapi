package com.lhpdesenvolvimentos.jobfast.job.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI jobfastOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
        .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(new Info()
                .title("Jobfast API")
                .description("Job aggregator and search platform")
                .version("1.0.0"));
    }
}
