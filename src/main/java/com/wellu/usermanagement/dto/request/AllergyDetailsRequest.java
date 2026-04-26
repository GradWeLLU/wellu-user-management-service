package com.wellu.usermanagement.dto.request;

public record AllergyDetailsRequest(
        Boolean hasAllergies,
        String allergies
) {
}
