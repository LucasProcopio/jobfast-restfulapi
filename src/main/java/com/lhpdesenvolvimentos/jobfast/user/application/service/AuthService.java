package com.lhpdesenvolvimentos.jobfast.user.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lhpdesenvolvimentos.jobfast.job.domain.exception.DomainException;
import com.lhpdesenvolvimentos.jobfast.user.application.dto.AuthResponse;
import com.lhpdesenvolvimentos.jobfast.user.domain.model.RefreshTokenEntity;
import com.lhpdesenvolvimentos.jobfast.user.domain.repository.RefreshTokenRepository;
import com.lhpdesenvolvimentos.jobfast.user.domain.repository.UserRepository;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthResponse authenticate(String email, String password) throws DomainException {
        var user = userRepository.findByEmail(email.toLowerCase().trim());
        if (user == null || user.isEmpty()) {
            throw new DomainException("Credenciais inválidas");
        }
        if (!user.isEnabled())
            throw new DomainException("Conta não verificada");
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new DomainException("Credenciais inválidas");

        String access = jwtService.generateAccessToken(user.getId().toString());
        String refresh = jwtService.generateRefreshToken(user.getId().toString());

        // armazenar hash do refresh token para revogação/rotacionamento
        RefreshTokenEntity tokenEntity = new RefreshTokenEntity();

        String hash = RefreshTokenEntity.hashToken(refresh);
        tokenEntity.setUser(user);
        tokenEntity.setTokenHash(hash);

        refreshTokenRepository.save(tokenEntity);

        return new AuthResponse(access, refresh, tokenEntity.getExpiresAt().toEpochMilli());
    }
}
