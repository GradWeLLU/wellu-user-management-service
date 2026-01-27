package com.wellu.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
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

    // probably best to compute this instead of storing it
    @Column
    private double BMI;

    @OneToOne(mappedBy = "profile")
    private User user;

    //Still need health profile, goals, and badges

    protected void setUser(User user){
        this.user = user;
    }
    public double calculateBMI(){
        return weight / (height * height);
    }
}
