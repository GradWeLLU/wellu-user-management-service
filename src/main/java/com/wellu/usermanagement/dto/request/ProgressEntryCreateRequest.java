package com.wellu.usermanagement.dto.request;

public record ProgressEntryCreateRequest(
         Double weight,
         Double caloriesBurnt,
         Integer workoutCompleted,
         String notes
) {
}
