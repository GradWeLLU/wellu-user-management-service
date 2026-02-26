package com.wellu.usermanagement.dto.request;

import java.util.List;
import java.util.UUID;

public record HealthProfileUpdateRequest(
        List<UUID> injuryIds,
        List<UUID> allergyIds,
        List<UUID> medicationIds
) {}