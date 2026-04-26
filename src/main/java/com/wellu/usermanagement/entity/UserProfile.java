package com.wellu.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "profiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column
    private int age;

    @Column
    private double weight;

    @Column
    private double height;

    //TODO: remove bmi hee and from db
    // probably best to compute this instead of storing it
    @Column
    private double BMI;

    @Column
    private String gender;

    @Column(name = "fitness_level")
    private String fitnessLevel;

    @Column(name = "main_goal")
    private String mainGoal;

    @OneToOne(mappedBy = "profile")
    private User user;

    @OneToOne(
            mappedBy = "userProfile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private HealthProfile healthProfile;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Badge> badges=new ArrayList<>();

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Goal> goals = new ArrayList<>();

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgressEntry> progressEntries = new ArrayList<>();

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Preference preference;

    protected void setUser(User user){
        this.user = user;
    }

    public void setHealthProfile(HealthProfile healthProfile) {
        this.healthProfile = healthProfile;
        if (healthProfile != null) {
            healthProfile.setUserProfile(this);
        }
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
        if (preference != null) {
            preference.setUserProfile(this);
        }
    }

    public double calculateBMI(){
        double heightInMeters = height > 3 ? height / 100.0 : height;
        return heightInMeters > 0 ? weight / (heightInMeters * heightInMeters) : 0;
    }


}
