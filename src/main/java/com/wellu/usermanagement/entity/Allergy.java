package com.wellu.usermanagement.entity;

import com.wellu.usermanagement.enumeration.SeverityLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "allergies")
public class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Lob
    @Column(nullable = true)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeverityLevel severityLevel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "health_profile_id", nullable = false)
    private HealthProfile healthProfile;

    void updateSeverityLevel(SeverityLevel level){
        this.severityLevel = level;
    }

    void setHealthProfile(HealthProfile profile){
        this.healthProfile = profile;
    }
}
