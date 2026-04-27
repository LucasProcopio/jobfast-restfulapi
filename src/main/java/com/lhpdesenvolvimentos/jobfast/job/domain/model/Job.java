package com.lhpdesenvolvimentos.jobfast.job.domain.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "jobs")
@Data 
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @JdbcTypeCode(org.hibernate.type.SqlTypes.LONGVARCHAR)
    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String companyName;

    @Column(nullable = true)
    private int maxSalary;

    @Column(nullable = true)
    private String location;

    @Column(nullable = true)
    private String seniority;

    @Column(nullable = true)
    private String jobType; // remote, hybrid ...

    @Column(nullable = true)
    private String applicationLink;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = true)           // optional — job may not have a post date yet
    private Date jobPostedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
