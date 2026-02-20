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

    protected void setUser(User user){
        this.user = user;
    }
    public double calculateBMI(){
        return weight / (height * height);
    }
}
