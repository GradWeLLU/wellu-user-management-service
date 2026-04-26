package com.wellu.usermanagement.dto.response;

public record MedicationDetailsResponse(
        boolean takesMedication,
        String medicationName,
        String reason
) {
}
