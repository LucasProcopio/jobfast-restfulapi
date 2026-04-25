package com.lhpdesenvolvimentos.jobfast.user.application.service;

import com.lhpdesenvolvimentos.jobfast.job.domain.exception.DomainException;
import com.lhpdesenvolvimentos.jobfast.user.application.dto.UserRegistrationRequest;
import com.lhpdesenvolvimentos.jobfast.user.domain.model.UserEntity;
import com.lhpdesenvolvimentos.jobfast.user.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void register(UserRegistrationRequest req) {
        // verify if user is already registered
        String email = req.email().trim().toLowerCase();
        if(userRepository.existsByEmail(email)) {
            throw new DomainException("Email já cadastrado no sistema.");
        }
        // could use passay to validate the password strength and complexity
        // the password encoder using securty Argon2 Industry standard
        String hashed = passwordEncoder.encode(req.password());
        UserEntity user = new UserEntity(email, hashed);
        userRepository.save(user);
    }
}
