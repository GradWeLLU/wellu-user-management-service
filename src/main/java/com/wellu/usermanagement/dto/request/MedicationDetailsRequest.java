package com.wellu.usermanagement.dto.request;

public record MedicationDetailsRequest(
        Boolean takesMedication,
        String medicationName,
        String reason
) {
}
