package com.wellu.usermanagement.dto.request;

import com.wellu.usermanagement.enumeration.SeverityLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record InjuryRequestDto(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull SeverityLevel severityLevel,
        Instant startDate,
        Instant endDate,
        boolean isChronic,
        @NotNull UUID healthProfileId
) {}