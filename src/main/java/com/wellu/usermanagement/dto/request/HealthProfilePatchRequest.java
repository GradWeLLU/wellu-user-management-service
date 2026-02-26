package com.wellu.usermanagement.dto.request;

import java.util.List;
import java.util.UUID;

public record HealthProfilePatchRequest(
        List<CreateInjuryRequest> injuriesToAdd,
        List<UUID> injuriesToRemove,

        List<CreateAllergyRequest> allergiesToAdd,
        List<UUID> allergiesToRemove,

        List<CreateMedicationRequest> medicationsToAdd,
        List<UUID> medicationsToRemove
) {
}
