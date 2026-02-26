package com.wellu.usermanagement.dto.response;

import java.util.List;
import java.util.UUID;

public record HealthProfileResponseDto(
        UUID id,
        List<InjuryResponseDto> injuries,
        List<AllergyResponseDto> allergies,
        List<MedicationResponseDto> medications,
        UUID userProfileId
) {
}
