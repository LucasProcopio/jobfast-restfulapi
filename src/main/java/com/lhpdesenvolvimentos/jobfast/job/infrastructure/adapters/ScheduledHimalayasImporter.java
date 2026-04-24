package com.lhpdesenvolvimentos.jobfast.job.infrastructure.adapters;

import com.lhpdesenvolvimentos.jobfast.job.application.dto.HimalayasResponse;
import com.lhpdesenvolvimentos.jobfast.job.application.mapper.JobMapper;
import com.lhpdesenvolvimentos.jobfast.job.domain.repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ScheduledHimalayasImporter {

    private final HimalayasClient himalayasClient;
    private final JobMapper jobMapper;
    private final JobRepository jobRepository;
    private static final int PAGE_SIZE = 500; // ajuste
    private static final long PAUSE_MS_BETWEEN_CALLS = 200L;
    private static final Logger log = LoggerFactory.getLogger(ScheduledHimalayasImporter.class);

    public ScheduledHimalayasImporter(HimalayasClient himalayasClient,
                                      JobMapper jobMapper,
                                      JobRepository jobRepository) {
        this.himalayasClient = himalayasClient;
        this.jobMapper = jobMapper;
        this.jobRepository = jobRepository;
    }

    // roda todo dia às 3:00
    @Scheduled(cron = "${job.import.cron}", zone = "UTC")
    @Async("importExecutor")
    public void runDailyImport() {
        importAllJobs();
    }

    // ou fixedDelay em ms
    // @Scheduled(fixedDelayString = "${job.import.fixed-delay-ms:3600000}")
    // public void runPeriodicImport() { importAllJobs(); }

    private void importAllJobs() {
        int offset = 0;
        int limit = PAGE_SIZE;
        while (true) {
            // adaptar call para aceitar offset, se API suportar
            HimalayasResponse resp = himalayasClient.callHimalayasApiWithOffset(limit, offset);

            if (resp == null || resp.jobs() == null || resp.jobs().isEmpty()) {
                break;
            }

            var entities = resp.jobs().stream()
                    .map(jobMapper::toJobEntity)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // grava em transação por lote (exemplo simples)
            try {
                jobRepository.saveAllAndFlush(entities);
            } catch (DataIntegrityViolationException e) {
                // se ocorrer duplicata por constraint unique, log e continue
                log.warn("Data integrity violation while saving jobs: {}", e.getMessage());
            }

            offset += resp.jobs().size();
            if (offset >= resp.totalCount()) break;

            try { Thread.sleep(PAUSE_MS_BETWEEN_CALLS); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); break; }
        }
    }
}