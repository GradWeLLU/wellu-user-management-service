package com.wellu.usermanagement.entity;

import com.wellu.usermanagement.enumeration.GoalType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name="goal_type", nullable = false)
    private GoalType goalType;

    @Column(name = "target_value", nullable = false)
    private Double targetValue;

    @Column(name = "current_value", nullable = false)
    private Double currentValue;

    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted = false;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    public void updateCurrentValue(double value) {
        this.currentValue = value;
        checkCompletion();
    }

    public boolean checkCompletion() {
        if (currentValue != null && targetValue != null && currentValue >= targetValue) {
            markCompleted();
            return true;
        }
        return false;
    }

    public void markCompleted() {
        this.isCompleted = true;
    }
}
