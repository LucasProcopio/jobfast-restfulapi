package com.lhpdesenvolvimentos.jobfast.profile.application.service;

import java.util.List;

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

    public AcademicExperienceResponse updateAcademicExperience(String userId, List<AcademicExperienceRequest> req) {
       // implement delete all by user id then insert new list of academic experience
        return new AcademicExperienceResponse("Academic experience created successfully");
    }
}
