package com.wellu.usermanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="progress_entries")
public class ProgressEntry {
    @Id
    @GeneratedValue
    @Column(name="id")
    private UUID id;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "calories_burnt", nullable = false)
    private Double caloriesBurnt;

    @Column(name = "workout_completed", nullable = false)
    private int workoutCompleted;

    @Column(name = "notes")
    private String notes;


    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;


}
