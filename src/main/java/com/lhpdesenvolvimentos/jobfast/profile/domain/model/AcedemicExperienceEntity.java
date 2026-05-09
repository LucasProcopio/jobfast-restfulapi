package com.lhpdesenvolvimentos.jobfast.profile.domain.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.lhpdesenvolvimentos.jobfast.profile.domain.error.AcademicExperienceException;
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
        NONE;

        @JsonCreator
        public static AcademicStatus from(String value) {
            if (value == null)
                return null;
            try {
                return AcademicStatus.valueOf(value.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new AcademicExperienceException("Invalid AcademicStatus: '" + value + "'. Acceptable values: "
                        + String.join(", ",
                                java.util.Arrays.stream(AcademicStatus.values()).map(Enum::name)
                                        .toArray(String[]::new)));
            }
        }
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

    public AcedemicExperienceEntity() {
    }

    public AcedemicExperienceEntity(Builder b) {
        this.institution = b.institution;
        this.course = b.course;
        this.status = b.status;
        this.educationLevel = b.educationLevel;
        this.acedemicQualification = b.acedemicQualification;
        this.startDate = b.startDate;
        this.endDate = b.endDate;
        this.user = b.user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String institution;
        private String course;
        private AcademicStatus status;
        private EducationLevel educationLevel;
        private AcademicQualification acedemicQualification;
        private LocalDate startDate;
        private LocalDate endDate;
        private UserEntity user;

        public Builder institution(String institution) {
            this.institution = institution;
            return this;
        }

        public Builder course(String course) {
            this.course = course;
            return this;
        }

        public Builder status(AcademicStatus status) {
            this.status = status;
            return this;
        }

        public Builder educationLevel(EducationLevel educationLevel) {
            this.educationLevel = educationLevel;
            return this;
        }

        public Builder acedemicQualification(AcademicQualification acedemicQualification) {
            this.acedemicQualification = acedemicQualification;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder user(UserEntity user) {
            this.user = user;
            return this;
        }

        public AcedemicExperienceEntity build() {
            return new AcedemicExperienceEntity(this);
        }
    }
}
