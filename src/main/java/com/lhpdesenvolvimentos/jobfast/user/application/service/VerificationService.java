package com.lhpdesenvolvimentos.jobfast.user.application.service;
import org.springframework.stereotype.Service;

import com.lhpdesenvolvimentos.jobfast.user.application.dto.VerificationResult;
import com.lhpdesenvolvimentos.jobfast.user.domain.error.UserVerificationException;
import com.lhpdesenvolvimentos.jobfast.user.domain.repository.VerificationTokenRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VerificationService {
    private final VerificationTokenRepository tokenRepository;
    private final UserService userService;

    public VerificationResult verifyToken(String token) {
        var tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty()) {
            return new VerificationResult(false, "Invalid token");
        }

        var verificationToken = tokenOpt.get();
        if (verificationToken.isExpired()) {
            return new VerificationResult(false, "Token expired");
        }
        // mark user as verified
        try {
            userService.markUserAsVerified(verificationToken.getUser());
        } catch (Exception e) {
            throw new UserVerificationException("Failed to verify user: " + e.getMessage());
        }

        tokenRepository.delete(verificationToken);
        return new VerificationResult(true, "User verified successfully");
    }

   
}
