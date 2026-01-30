package com.wellu.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "health_profiles")
public class HealthProfile {
    @Id
    @GeneratedValue(strategy =GenerationType.UUID)
    @Column(unique = true, updatable = false, nullable = false)
    private UUID id;

    @OneToMany(
            mappedBy = "healthProfile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Injury> injuries = new ArrayList<>();

    @OneToMany(
            mappedBy = "healthProfile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Allergy> allergies = new ArrayList<>();

    @OneToMany(
            mappedBy = "healthProfile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Medication> medications = new ArrayList<>();

    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "user_profile_id",
            nullable = false,
            unique = true
    )
    private UserProfile userProfile;


    //Injury helpers
    public void addInjury(Injury injury){
        this.injuries.add(injury);
        injury.setHealthProfile(this);
    }
    public void removeInjury(Injury injury){
        this.injuries.remove(injury);
        injury.setHealthProfile(null);
    }

    //Allergy helpers

    public void addAllergy(Allergy allergy){
        this.allergies.add(allergy);
        allergy.setHealthProfile(this);
    }

    public void removeAllergy(Allergy allergy){
        this.allergies.remove(allergy);
        allergy.setHealthProfile(null);
    }

    //Medication helpers

    public void addMedication(Medication medication){
        this.medications.add(medication);
        medication.setHealthProfile(this);
    }

    public void removeMedication(Medication medication){
        this.medications.remove(medication);
        medication.setHealthProfile(null);
    }

}
