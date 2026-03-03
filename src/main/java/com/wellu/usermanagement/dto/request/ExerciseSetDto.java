package com.wellu.usermanagement.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseSetDto {
    private UUID id;          // Only for responses / updates
    @Min(0)
    private Integer reps;
    @DecimalMin("0.0")
    private Double weight;
}
