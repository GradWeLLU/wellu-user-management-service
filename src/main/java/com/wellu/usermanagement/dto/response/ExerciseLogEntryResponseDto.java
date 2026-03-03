package com.wellu.usermanagement.dto.response;

import com.wellu.usermanagement.dto.request.ExerciseSetDto;
import com.wellu.usermanagement.enumeration.ExerciseType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseLogEntryResponseDto {

    private UUID id;
    private String exerciseName;
    private ExerciseType type;

    // Cardio
    private Integer durationMinutes;
    private Double distanceKm;
    private Integer burnedCalories;

    // Strength
    private List<ExerciseSetDto> sets;
}