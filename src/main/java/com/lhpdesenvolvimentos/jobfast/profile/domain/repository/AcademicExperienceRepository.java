package com.lhpdesenvolvimentos.jobfast.profile.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lhpdesenvolvimentos.jobfast.profile.domain.model.AcedemicExperienceEntity;

@Repository
public interface AcademicExperienceRepository extends JpaRepository<AcedemicExperienceEntity, Long> {
    Optional<List<AcedemicExperienceEntity>> findByUserId(String userId);
    void deleteByUserId(String userId);
}