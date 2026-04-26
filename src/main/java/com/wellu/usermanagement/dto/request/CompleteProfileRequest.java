package com.wellu.usermanagement.dto.request;

public record CompleteProfileRequest(
        String gender,
        Integer age,
        Double height,
        Double weight,
        String fitnessLevel,
        String mainGoal,
        Integer duration,
        String intensity,
        String workoutTime,
        String diet,
        InjuryDetailsRequest injuryDetails,
        MedicationDetailsRequest medicationDetails,
        AllergyDetailsRequest allergyDetails
) {
}
