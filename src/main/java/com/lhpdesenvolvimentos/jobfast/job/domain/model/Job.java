package com.lhpdesenvolvimentos.jobfast.job.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "jobs")
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

    // Setters
    public void setTitle(String title) { this.title = title; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public void setDescription(String description) { this.description = description ; }

    public void setApplicationLink(String applicationLink) { this.applicationLink = applicationLink; }

    public void setJobPostedAt(Date jobPostedAt) { this.jobPostedAt = jobPostedAt; }

    public void setJobType(String jobType) { this.jobType = jobType; }

    public void setLocation(String location) { this.location = location; }

    public void setMaxSalary(int maxSalary) { this.maxSalary = maxSalary; }

    public void setSeniority(String seniority) { this.seniority = seniority; }

    // Getters
    public String getId() {
        return id;
    }
}
