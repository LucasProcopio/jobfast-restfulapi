package com.lhpdesenvolvimentos.jobfast.profile.application.service;

import org.springframework.stereotype.Service;

import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AcademicExperienceRequest;
import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AcademicExperienceResponse;
import com.lhpdesenvolvimentos.jobfast.profile.domain.repository.AcademicExperienceRepository;

@Service
public class AcademicExperienceService {
    private AcademicExperienceRepository academicExperienceRepository;

    public AcademicExperienceService(AcademicExperienceRepository academicExperienceRepository) {
        this.academicExperienceRepository = academicExperienceRepository;
    }

    public AcademicExperienceResponse createAcademicExperience(String userId, AcademicExperienceRequest req) {
        // implement academic experience creation logic here, e.g. save to database
        return new AcademicExperienceResponse("Academic experience created successfully");
    }
}
