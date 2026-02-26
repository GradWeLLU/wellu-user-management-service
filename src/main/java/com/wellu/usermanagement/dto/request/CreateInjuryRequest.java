package com.wellu.usermanagement.dto.request;

import com.wellu.usermanagement.enumeration.SeverityLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CreateInjuryRequest(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull SeverityLevel severityLevel,
        Instant startDate,
        Instant endDate,
        boolean isChronic
) {}