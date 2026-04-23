package com.lhpdesenvolvimentos.jobfast.job.application.mapper;

import com.lhpdesenvolvimentos.jobfast.job.application.dto.HimalayasJob;
import com.lhpdesenvolvimentos.jobfast.job.domain.model.Job;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobMapper {

    public   Job toJobEntity(HimalayasJob dto) {
        Job job = new Job();
        job.setTitle(dto.title());
        job.setCompanyName(dto.companyName());
        job.setLocation(dto.location());
        job.setDescription(dto.description());
        job.setApplicationLink(dto.applicationLink());
        job.setJobType(dto.employmentType());
        job.setJobPostedAt(dto.pubDate());
        job.setMaxSalary(dto.maxSalary() != null ? dto.maxSalary() : 0);
        job.setSeniority(dto.seniority() != null && dto.seniority().length > 0 ? dto.seniority()[0] : "Not specified");
        return job;
    }

    public  List<Job> toJobEntityList(List<HimalayasJob> dtos) {
        return dtos.stream()
                .map(this::toJobEntity)
                .toList();
    }
}
