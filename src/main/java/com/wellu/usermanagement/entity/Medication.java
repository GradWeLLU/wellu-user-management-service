package com.wellu.usermanagement.entity;

import com.wellu.usermanagement.enumeration.SeverityLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    @Positive
    private Double dosage;

    @Column(nullable = false)
    @Positive
    private int frequency;

    @Column(nullable = false)
    private Instant startDate;

    @Column(nullable = true)
    private Instant endDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "health_profile_id", nullable = false)
    private HealthProfile healthProfile;


    public void setHealthProfile(HealthProfile profile){
        this.healthProfile = profile;
    }

    public void updateDosage(double dosage){
        if( dosage <= 0){
            throw new IllegalArgumentException("Dosage must be positive");
        }
        this.dosage = dosage;
    }

    public void updateFrequency(int frequency){
        if(frequency <= 0){
            throw new IllegalArgumentException("Frequency must be positive");
        }
        this.frequency = frequency;
    }

}
