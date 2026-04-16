package com.wellu.usermanagement.dto.response;

import java.util.List;
import java.util.UUID;

public record WorkoutPlanRequestDTO(
        UUID userID,
        String goal,
        int age,
        double weight,
        double height,
        double bmi,
        Integer sessionDuration,
        String preferredDifficultyLevel,
        List<String> preferredEquipment,
        String experienceLevel,
        List<String> injuries
) {
}
