package com.wellu.usermanagement.entity;

import com.wellu.usermanagement.enumeration.SeverityLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name ="injuries")
public class Injury {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id",nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="description",nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name="severity_level",nullable = false)
    private SeverityLevel severityLevel;

    @Column(name="start_date")
    private Instant startDate;

    @Column(name="end_date")
    private Instant endDate;

    @Column(name="is_chronic",nullable = false)
    private boolean isChronic;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "health_profile_id", nullable = false)
    private HealthProfile healthProfile;

    public void updateSeverityLevel(SeverityLevel level){
        this.severityLevel = level;
    }

    void setHealthProfile(HealthProfile profile){
        this.healthProfile = profile;
    }
}


