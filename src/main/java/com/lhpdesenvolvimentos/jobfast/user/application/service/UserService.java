package com.lhpdesenvolvimentos.jobfast.user.application.service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lhpdesenvolvimentos.jobfast.job.domain.exception.DomainException;
import com.lhpdesenvolvimentos.jobfast.user.application.dto.UserRegistrationRequest;
import com.lhpdesenvolvimentos.jobfast.user.domain.model.UserEntity;
import com.lhpdesenvolvimentos.jobfast.user.domain.model.VerificationTokenEntity;
import com.lhpdesenvolvimentos.jobfast.user.domain.repository.UserRepository;
import com.lhpdesenvolvimentos.jobfast.user.domain.repository.VerificationTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;
    
    @Transactional
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

        // generate verification token and send email
        String token = UUID.randomUUID().toString();
        VerificationTokenEntity verificationToken = new VerificationTokenEntity(token, user, Instant.now().plus(Duration.ofHours(24)));
        verificationTokenRepository.save(verificationToken);

        // send async email
        emailService.sendVerificationEmail(email, token);
    }

    public void markUserAsVerified(UserEntity user) {
        user.setEnabled(true);
        userRepository.save(user);
    }
}
