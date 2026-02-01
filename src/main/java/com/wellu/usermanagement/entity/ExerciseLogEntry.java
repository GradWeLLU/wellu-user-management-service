package com.wellu.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Exercise_Log_Entries")
public class ExerciseLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "exercise_id")
    private UUID exerciseID;

    @Column(name = "sets")
    private int sets;
    @Column(name = "reps")
    private int reps;
    @Column(name = "weights")
    private double weights;

    @ManyToOne
    @JoinColumn(name = "exercise_log_id")
    private ExerciseLog exerciseLog;

}
