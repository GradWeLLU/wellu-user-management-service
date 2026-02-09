package com.wellu.usermanagement.dto.request;

import jakarta.validation.constraints.Positive;

import java.time.Instant;

public record MedicationUpdateRequestDto(

        @Positive
        Double dosage,

        @Positive
        Integer frequency,

        Instant endDate

) {}
