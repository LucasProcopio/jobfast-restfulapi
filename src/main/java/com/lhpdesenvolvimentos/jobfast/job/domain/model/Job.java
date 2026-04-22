package com.lhpdesenvolvimentos.jobfast.job.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

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
    private LocalDateTime jobPostedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
