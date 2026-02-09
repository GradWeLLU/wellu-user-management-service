package com.wellu.usermanagement.dto.response;

import java.util.List;
import java.util.UUID;

public record HealthProfileResponseDto(
        UUID id,
        List<UUID> injuryIds,
        List<UUID> allergyIds,
        List<UUID> medicationIds,
        UUID userProfileId
) {
}
