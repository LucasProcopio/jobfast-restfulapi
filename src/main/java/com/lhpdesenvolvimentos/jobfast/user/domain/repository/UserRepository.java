package com.lhpdesenvolvimentos.jobfast.user.domain.repository;

import com.lhpdesenvolvimentos.jobfast.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
