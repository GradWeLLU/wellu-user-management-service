package com.wellu.usermanagement.dto.response;

import com.wellu.usermanagement.enumeration.SeverityLevel;

import java.util.UUID;

public record AllergyResponseDto(
        UUID id,
        String name,
        String description,
        SeverityLevel severityLevel
) {
}
