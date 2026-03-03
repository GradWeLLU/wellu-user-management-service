package com.wellu.usermanagement.entity;

import com.wellu.usermanagement.enumeration.ExerciseType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Generated;
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
@Table(name = "exercise_entries")
public class ExerciseLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String exerciseName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExerciseType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_log_id", nullable = false)
    private ExerciseLog exerciseLog;

    @OneToMany(mappedBy = "exerciseEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ExerciseSet> sets = new ArrayList<>();

    // For cardio only
    @Min(0)
    private Integer durationMinutes;
    @DecimalMin("0.0")
    private Double distanceKm;
    @Min(0)
    private Integer burnedCalories;

    public void addSet(ExerciseSet set) {
        sets.add(set);
        set.setExerciseEntry(this);
    }
}
