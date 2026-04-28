package com.lhpdesenvolvimentos.jobfast.user.domain.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "refresh_tokens", indexes = {
        @Index(name = "idx_refresh_token_hash", columnList = "token_hash"),
        @Index(name = "idx_refresh_token_user", columnList = "user_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Armazene o hash (ex: SHA-256 -> base64) do token, nunca o token em texto puro
    @Column(name = "token_hash", nullable = false, length = 128, unique = true)
    private String tokenHash;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "issued_at", nullable = false)
    private Instant issuedAt;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "revoked", nullable = false)
    private boolean revoked = false;

    // Guarda hash do token que substituiu este (rotacionamento), opcional
    @Column(name = "replaced_by_hash", nullable = true, length = 128)
    private String replacedByHash;

    // Opcionalmente armazenar meta para auditoria
    @Column(name = "created_by_ip", nullable = true, length = 100)
    private String createdByIp;

    @Column(name = "user_agent", nullable = true, length = 512)
    private String userAgent;

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiresAt);
    }

    public void revoke(String replacedByHash) {
        this.revoked = true;
        this.replacedByHash = replacedByHash;
    }

    @PrePersist
    private void onCreate() {
        this.issuedAt = Instant.now();
        this.expiresAt = this.issuedAt.plusSeconds(7 * 24 * 60 * 60); // expira em 7 dias
    }

    // Utilitário simples para gerar hash do token (SHA-256 -> base64)
    public static String hashToken(String token) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(token.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 não disponível", e);
        }
    }
}