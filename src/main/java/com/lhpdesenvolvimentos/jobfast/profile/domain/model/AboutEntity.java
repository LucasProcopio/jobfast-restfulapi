package com.lhpdesenvolvimentos.jobfast.profile.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.Instant;

import com.lhpdesenvolvimentos.jobfast.user.domain.model.UserEntity;

@Entity
@Data
@Table(name = "about")
public class AboutEntity {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private boolean disability = false;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String cityName;

    @Column(nullable = false)
    private String state;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    private void onCreate() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = Instant.now();
    }

    private AboutEntity(Builder b) {
        this.zipCode = b.zipCode;
        this.address = b.address;
        this.cityName = b.cityName;
        this.state = b.state;
        this.disability = b.disability;
        this.gender = b.gender;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String zipCode;
        private String address;
        private String cityName;
        private String state;
        private boolean disability;
        private Gender gender;

        public Builder zipCode(String zipCode) { this.zipCode = zipCode; return this; }
        public Builder address(String address) { this.address = address; return this; }
        public Builder cityName(String cityName) { this.cityName = cityName; return this; }
        public Builder state(String state) { this.state = state; return this; }
        public Builder disability(boolean disability) { this.disability = disability; return this; }
        public Builder gender(Gender gender) { this.gender = gender; return this; }

        public AboutEntity build() { return new AboutEntity(this); }
    }

    public enum Gender {
        MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY, NON_BINARY
    }
}
