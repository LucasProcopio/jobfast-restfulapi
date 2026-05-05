package com.lhpdesenvolvimentos.jobfast.profile.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lhpdesenvolvimentos.jobfast.profile.domain.model.AboutEntity;

@Repository
public interface AboutRepository extends JpaRepository<AboutEntity, Long> {
    Optional<AboutEntity> findByUserId(String userId);
}
