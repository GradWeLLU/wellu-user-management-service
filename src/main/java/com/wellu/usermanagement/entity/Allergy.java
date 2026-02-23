package com.wellu.usermanagement.entity;

import com.wellu.usermanagement.enumeration.SeverityLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "allergies")
public class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

//    @Lob
    @Column(nullable = true)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name="severity_level",nullable = false)
    private SeverityLevel severityLevel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "health_profile_id", nullable = false)
    private HealthProfile healthProfile;

    public void updateSeverityLevel(SeverityLevel level){
        this.severityLevel = level;
    }

    public void setHealthProfile(HealthProfile profile){
        this.healthProfile = profile;
    }
}
