package com.wellu.usermanagement.dto.response;

public record AllergyDetailsResponse(
        boolean hasAllergies,
        String allergies
) {
}
