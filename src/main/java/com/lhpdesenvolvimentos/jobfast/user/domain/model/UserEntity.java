package com.lhpdesenvolvimentos.jobfast.user.domain.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lhpdesenvolvimentos.jobfast.profile.domain.model.AcedemicExperienceEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean enabled = false;
    private Instant createdAt;
    private Instant updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = jakarta.persistence.FetchType.LAZY)
    @JsonManagedReference
    private List<AcedemicExperienceEntity> academicExperiences = new ArrayList<>();

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected UserEntity() {
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public boolean isEmpty() {
        return this.id == null || this.id.isBlank();
    }

    public void addAcademicExperience(AcedemicExperienceEntity exp) {
        academicExperiences.add(exp);
        exp.setUser(this);
    }

    public void removeAcademicExperience(AcedemicExperienceEntity exp) {
        academicExperiences.remove(exp);
        exp.setUser(null);
    }
}
