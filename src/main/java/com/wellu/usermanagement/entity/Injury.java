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
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeverityLevel level;

    @Column
    private Instant startDate;

    @Column
    private Instant endDate;

    @Column(nullable = false)
    private boolean chronic;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "health_profile_id", nullable = false)
    private HealthProfile healthProfile;

    public void updateSeverityLevel(SeverityLevel level){
        this.level = level;
    }

    void setHealthProfile(HealthProfile profile){
        this.healthProfile = profile;
    }
}


