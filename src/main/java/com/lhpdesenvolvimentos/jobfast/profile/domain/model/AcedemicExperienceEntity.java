package com.lhpdesenvolvimentos.jobfast.profile.domain.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lhpdesenvolvimentos.jobfast.user.domain.model.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "academic_experience")
public class AcedemicExperienceEntity {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String institution;

    @NotBlank
    private String course;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private AcademicStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private EducationLevel educationLevel;

    @Enumerated(EnumType.STRING)
    private AcademicQualification acedemicQualification;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(optional = false, fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity user;

    public enum AcademicStatus {
        ONGOING,
        COMPLETED,
        INTERRUPTED,
        PLANNED,
        NONE,
    }

    public enum EducationLevel {
        NONE,
        HIGH_SCHOOL,
        TECHNICAL,
        BACHELOR,
        MASTER,
        DOCTORATE,
        OTHER
    }

    public enum AcademicQualification {
        NONE,
        HIGH_SCHOOL_DIPLOMA,
        TECHNICAL_CERTIFICATE,
        BACHELOR_DEGREE,
        MASTER_DEGREE,
        DOCTORATE_DEGREE,
        ASSOCIATE_DEGREE,
        OTHER
    }
}
