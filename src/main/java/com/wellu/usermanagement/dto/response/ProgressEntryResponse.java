package com.wellu.usermanagement.dto.response;

import java.time.Instant;
import java.util.UUID;

public record ProgressEntryResponse(

        UUID id,
         Double weight,
         Double caloriesBurnt,
         Integer workoutCompleted,
         String notes,
         Instant recordedAt
) {
}
