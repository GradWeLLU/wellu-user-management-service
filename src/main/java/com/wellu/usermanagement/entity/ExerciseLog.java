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
@Table(name = "Exercise_logs")
public class ExerciseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;
    @Column(name = "workout_day")
    private UUID workoutDay;
    @Column(name = "user_ID")
    private UUID userID;

    @OneToMany(mappedBy = "exerciseLog", cascade = CascadeType.ALL)
    private List<ExerciseLogEntry> entries = new ArrayList<>();

}
