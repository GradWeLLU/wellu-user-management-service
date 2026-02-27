package com.wellu.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "exercise_logs")
public class ExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "exerciseLog",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ExerciseLogEntry> entries = new ArrayList<>();
    @Column(nullable = false)
    private LocalDate workoutDate;

    public void addEntry(ExerciseLogEntry entry) {
        entries.add(entry);
        entry.setExerciseLog(this);
    }

}