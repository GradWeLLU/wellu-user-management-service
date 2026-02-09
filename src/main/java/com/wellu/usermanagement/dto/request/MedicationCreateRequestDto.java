package com.wellu.usermanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.UUID;

public record MedicationCreateRequestDto(

        @NotBlank
        String name,

        @NotNull
        @Positive
        Double dosage,

        @Positive
        int frequency,

        @NotNull
        Instant startDate,

        Instant endDate,

        @NotNull
        UUID healthProfileId

) {}
