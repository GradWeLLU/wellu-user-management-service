package com.wellu.usermanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public record CreateMedicationRequest(
        @NotBlank String name,
        @Positive @NotBlank Double dosage,
        @Positive @NotBlank int frequency,
        @NotBlank Instant startDate,
        Instant endDate
) {
}
