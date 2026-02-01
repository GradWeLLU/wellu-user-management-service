package com.wellu.usermanagement.dto.response;

import com.wellu.usermanagement.enumeration.SeverityLevel;

import java.time.Instant;
import java.util.UUID;

public record InjuryResponseDto(
        UUID id,
        String name,
        String description,
        SeverityLevel severityLevel,
        Instant startDate,
        Instant endDate,
        boolean isChronic,
        UUID healthProfileId
) {}
