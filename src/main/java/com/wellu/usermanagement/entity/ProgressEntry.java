package com.wellu.usermanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="progress_entry")
public class ProgressEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="progress_entry_id")
    private Long id;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "calories_burnt", nullable = false)
    private Double caloriesBurnt;

    @Column(name = "workout_completed", nullable = false)
    private int workoutCompleted;

    @Column(name = "notes")
    private String notes;


    @Column(name = "recorded_at", nullable = false)
    private LocalDate recordedAt;

    @ManyToOne
    @JoinColumn(name = "profiles", nullable = false)
    private UserProfile userProfile;


}
