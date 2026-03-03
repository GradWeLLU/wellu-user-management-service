package com.wellu.usermanagement.dto.request;

import com.wellu.usermanagement.enumeration.ExerciseType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseLogEntryRequestDto {

    @NotBlank
    private String exerciseName;

    @NotNull
    private ExerciseType type;

    // Cardio fields (optional for strength)
    @Min(0)
    private Integer durationMinutes;
    @DecimalMin("0.0")
    private Double distanceKm;
    @Min(0)
    private Integer burnedCalories;

    // Strength sets
    private List<ExerciseSetDto> sets;
}
