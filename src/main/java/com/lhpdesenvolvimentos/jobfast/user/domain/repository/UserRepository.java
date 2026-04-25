package com.lhpdesenvolvimentos.jobfast.user.domain.repository;

import com.lhpdesenvolvimentos.jobfast.user.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
}
