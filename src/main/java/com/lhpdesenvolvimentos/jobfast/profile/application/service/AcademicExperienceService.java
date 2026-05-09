package com.lhpdesenvolvimentos.jobfast.profile.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.security.autoconfigure.SecurityProperties.User;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.lhpdesenvolvimentos.jobfast.job.domain.exception.DomainException;
import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AcademicExperienceRequest;
import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AcademicExperienceResponse;
import com.lhpdesenvolvimentos.jobfast.profile.domain.model.AcedemicExperienceEntity;
import com.lhpdesenvolvimentos.jobfast.profile.domain.repository.AcademicExperienceRepository;
import com.lhpdesenvolvimentos.jobfast.user.domain.model.UserEntity;
import com.lhpdesenvolvimentos.jobfast.user.domain.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AcademicExperienceService {
    private AcademicExperienceRepository academicExperienceRepository;
    private UserRepository userRepository;

    public AcademicExperienceService(AcademicExperienceRepository academicExperienceRepository,
            UserRepository userRepository) {
        this.academicExperienceRepository = academicExperienceRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AcademicExperienceResponse updateAcademicExperience(String userId, List<AcademicExperienceRequest> req) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException("User not found"));

        List<AcedemicExperienceEntity> entities = req.stream()
                .map(r -> AcedemicExperienceEntity.builder()
                        .institution(r.institution())
                        .course(r.course())
                        .status(r.status())
                        .acedemicQualification(r.academicQualification())
                        .educationLevel(r.educationLevel())
                        .startDate(r.startDate())
                        .endDate(r.endDate())
                        .user(user)
                        .build())
                .collect(Collectors.toList());

        try {
            academicExperienceRepository.deleteByUserId(userId);
            academicExperienceRepository.saveAll(entities);
        } catch (DataAccessException ex) {
            log.error("DB error updating academic experiences for user {}: {}", userId, ex.getMessage(), ex);
            throw new DomainException("Failed to update academic experiences", ex); // Runtime -> rollback
        }
        return new AcademicExperienceResponse("Academic experience created successfully");
    }
}
