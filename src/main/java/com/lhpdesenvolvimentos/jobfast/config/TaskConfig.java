package com.lhpdesenvolvimentos.jobfast.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableScheduling
@EnableAsync
public class TaskConfig {

    @Bean("importExecutor")
    public Executor importExecutor() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        exec.setCorePoolSize(2);        // threads mínimas
        exec.setMaxPoolSize(4);         // threads máximas
        exec.setQueueCapacity(20);      // quantos tasks podem ficar na fila
        exec.setThreadNamePrefix("import-exec-");
        exec.setAwaitTerminationSeconds(30);
        exec.initialize();
        return exec;
    }
}
