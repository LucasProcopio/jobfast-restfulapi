package com.lhpdesenvolvimentos.jobfast.user.domain.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "verification_tokens")
public class VerificationTokenEntity {
    @Id @GeneratedValue
    private Long id;
    private String token;
    private Instant expiryDate;

    @OneToOne
    private UserEntity user;

    public boolean isExpired() {
        return Instant.now().isAfter(expiryDate);
    }

    public VerificationTokenEntity() {}

    public VerificationTokenEntity(String token, UserEntity user, Instant expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }
}
