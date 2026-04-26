package com.wellu.usermanagement.dto.response;

public record CompleteProfileResponse(
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
        InjuryDetailsResponse injuryDetails,
        MedicationDetailsResponse medicationDetails,
        AllergyDetailsResponse allergyDetails
) {
}
