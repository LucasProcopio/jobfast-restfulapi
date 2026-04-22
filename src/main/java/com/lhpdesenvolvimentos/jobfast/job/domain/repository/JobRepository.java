package com.lhpdesenvolvimentos.jobfast.job.domain.repository;

import com.lhpdesenvolvimentos.jobfast.job.domain.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {
}
