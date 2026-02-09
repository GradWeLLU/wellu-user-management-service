package com.wellu.usermanagement.dto.response;

import java.time.Instant;
import java.util.UUID;

public record MedicationResponseDto(

        UUID id,
        String name,
        Double dosage,
        int frequency,
        Instant startDate,
        Instant endDate,
        UUID healthProfileId

) {}
