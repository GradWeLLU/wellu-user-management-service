package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.AllergyDetailsRequest;
import com.wellu.usermanagement.dto.request.CompleteProfileRequest;
import com.wellu.usermanagement.dto.request.InjuryDetailsRequest;
import com.wellu.usermanagement.dto.request.MedicationDetailsRequest;
import com.wellu.usermanagement.dto.response.AllergyDetailsResponse;
import com.wellu.usermanagement.dto.response.CompleteProfileResponse;
import com.wellu.usermanagement.dto.response.InjuryDetailsResponse;
import com.wellu.usermanagement.dto.response.MedicationDetailsResponse;
import com.wellu.usermanagement.entity.Allergy;
import com.wellu.usermanagement.entity.HealthProfile;
import com.wellu.usermanagement.entity.Injury;
import com.wellu.usermanagement.entity.Medication;
import com.wellu.usermanagement.entity.Preference;
import com.wellu.usermanagement.entity.User;
import com.wellu.usermanagement.entity.UserProfile;
import com.wellu.usermanagement.enumeration.DietaryType;
import com.wellu.usermanagement.enumeration.DifficultyLevel;
import com.wellu.usermanagement.enumeration.SeverityLevel;
import com.wellu.usermanagement.enumeration.TimePeriod;
import com.wellu.usermanagement.exception.UserNotFoundException;
import com.wellu.usermanagement.repository.PreferenceRepository;
import com.wellu.usermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileOnboardingService {

    private final UserRepository userRepository;

    @Transactional
    public CompleteProfileResponse completeMyProfile(CompleteProfileRequest request) {
        User user = getCurrentUser();
        UserProfile profile = getOrCreateProfile(user);
        HealthProfile healthProfile = getOrCreateHealthProfile(profile);
        Preference preference = getOrCreatePreference(profile);

        applyProfileUpdates(profile, request);
        applyPreferenceUpdates(preference, request);
        applyInjuryDetails(healthProfile, request.injuryDetails());
        applyMedicationDetails(healthProfile, request.medicationDetails());
        applyAllergyDetails(healthProfile, request.allergyDetails());

        userRepository.save(user);
        return buildResponse(profile, healthProfile, preference);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());

        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private UserProfile getOrCreateProfile(User user) {
        UserProfile profile = user.getProfile();
        if (profile == null) {
            profile = new UserProfile();
            user.setProfile(profile);
        }
        return profile;
    }

    private HealthProfile getOrCreateHealthProfile(UserProfile profile) {
        HealthProfile healthProfile = profile.getHealthProfile();
        if (healthProfile == null) {
            healthProfile = new HealthProfile();
            healthProfile.setUserProfile(profile);
            profile.setHealthProfile(healthProfile);
        }
        return healthProfile;
    }

    private Preference getOrCreatePreference(UserProfile profile) {
        Preference preference = profile.getPreference();
        if (preference == null) {
            preference = new Preference();
            profile.setPreference(preference);
        }
        return preference;
    }

    private void applyProfileUpdates(UserProfile profile, CompleteProfileRequest request) {
        profile.setGender(request.gender());
        profile.setFitnessLevel(request.fitnessLevel());
        profile.setMainGoal(request.mainGoal());

        if (request.age() != null) {
            profile.setAge(request.age());
        }
        if (request.height() != null) {
            profile.setHeight(request.height());
        }
        if (request.weight() != null) {
            profile.setWeight(request.weight());
        }
        if (profile.getWeight() > 0 && profile.getHeight() > 0) {
            profile.setBMI(profile.calculateBMI());
        }
    }

    private void applyPreferenceUpdates(Preference preference, CompleteProfileRequest request) {
        preference.setPreferredWorkoutDuration(request.duration() != null ? request.duration() : 60);
        preference.setPreferredDifficulty(parseDifficulty(request.intensity()));

        preference.getPreferredTimePeriods().clear();
        TimePeriod workoutTime = parseTimePeriod(request.workoutTime());
        if (workoutTime != null) {
            preference.addPreferredTime(workoutTime);
        }

        preference.getPreferredDietaryType().clear();
        DietaryType diet = parseDietaryType(request.diet());
        if (diet != null) {
            preference.getPreferredDietaryType().add(diet);
        }
    }

    private void applyInjuryDetails(HealthProfile healthProfile, InjuryDetailsRequest details) {
        healthProfile.getInjuries().clear();

        if (details == null || !Boolean.TRUE.equals(details.hasInjury()) || details.areas() == null) {
            return;
        }

        for (String area : details.areas()) {
            if (area == null || area.isBlank()) {
                continue;
            }

            Injury injury = new Injury(
                    area.trim(),
                    "Added from onboarding flow",
                    SeverityLevel.MODERATE,
                    details.startDate(),
                    details.endDate(),
                    Boolean.TRUE.equals(details.stillFeelIt())
            );
            healthProfile.addInjury(injury);
        }
    }

    private void applyMedicationDetails(HealthProfile healthProfile, MedicationDetailsRequest details) {
        healthProfile.getMedications().clear();

        if (details == null || !Boolean.TRUE.equals(details.takesMedication()) || isBlank(details.medicationName())) {
            return;
        }

        Medication medication = new Medication();
        medication.setName(details.medicationName().trim());
        medication.setReason(details.reason());
        healthProfile.addMedication(medication);
    }

    private void applyAllergyDetails(HealthProfile healthProfile, AllergyDetailsRequest details) {
        healthProfile.getAllergies().clear();

        if (details == null || !Boolean.TRUE.equals(details.hasAllergies()) || isBlank(details.allergies())) {
            return;
        }

        Arrays.stream(details.allergies().split(","))
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .forEach(name -> {
                    Allergy allergy = new Allergy(name, null, SeverityLevel.LOW);
                    healthProfile.addAllergy(allergy);
                });
    }

    private CompleteProfileResponse buildResponse(UserProfile profile,
                                                  HealthProfile healthProfile,
                                                  Preference preference) {
        List<String> injuryAreas = healthProfile.getInjuries().stream()
                .map(Injury::getName)
                .distinct()
                .toList();

        Injury firstInjury = healthProfile.getInjuries().stream().findFirst().orElse(null);
        InjuryDetailsResponse injuryDetails = new InjuryDetailsResponse(
                !injuryAreas.isEmpty(),
                injuryAreas,
                firstInjury != null ? firstInjury.getStartDate() : null,
                firstInjury != null ? firstInjury.getEndDate() : null,
                firstInjury != null && firstInjury.isChronic()
        );

        Medication firstMedication = healthProfile.getMedications().stream().findFirst().orElse(null);
        MedicationDetailsResponse medicationDetails = new MedicationDetailsResponse(
                firstMedication != null,
                firstMedication != null ? firstMedication.getName() : null,
                firstMedication != null ? firstMedication.getReason() : null
        );

        String allergies = healthProfile.getAllergies().stream()
                .map(Allergy::getName)
                .distinct()
                .reduce((left, right) -> left + ", " + right)
                .orElse(null);

        AllergyDetailsResponse allergyDetails = new AllergyDetailsResponse(
                allergies != null && !allergies.isBlank(),
                allergies
        );

        return new CompleteProfileResponse(
                profile.getGender(),
                profile.getAge(),
                profile.getHeight(),
                profile.getWeight(),
                profile.getFitnessLevel(),
                profile.getMainGoal(),
                preference.getPreferredWorkoutDuration(),
                formatDifficulty(preference.getPreferredDifficulty()),
                formatWorkoutTime(preference.getPreferredTimePeriods().stream().findFirst().orElse(null)),
                formatDiet(preference.getPreferredDietaryType().stream().findFirst().orElse(null)),
                injuryDetails,
                medicationDetails,
                allergyDetails
        );
    }

    private DifficultyLevel parseDifficulty(String intensity) {
        if (isBlank(intensity)) {
            return null;
        }

        return switch (intensity.trim().toUpperCase()) {
            case "BEGINNER", "LOW", "EASY" -> DifficultyLevel.BEGINNER;
            case "INTERMEDIATE", "MEDIUM", "MODERATE" -> DifficultyLevel.INTERMEDIATE;
            case "ADVANCED", "HIGH", "HARD" -> DifficultyLevel.ADVANCED;
            default -> DifficultyLevel.valueOf(intensity.trim().toUpperCase());
        };
    }

    private TimePeriod parseTimePeriod(String workoutTime) {
        if (isBlank(workoutTime)) {
            return null;
        }

        return switch (workoutTime.trim().toUpperCase()) {
            case "NONE" -> TimePeriod.None;
            default -> TimePeriod.valueOf(workoutTime.trim().toUpperCase());
        };
    }

    private DietaryType parseDietaryType(String diet) {
        if (isBlank(diet)) {
            return null;
        }
        String normalized = diet.trim().toUpperCase().replace('-', '_').replace(' ', '_');

        return switch (normalized) {
            case "BALANCED", "NONE" -> DietaryType.NONE;
            default -> DietaryType.valueOf(normalized);
        };
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private String formatDifficulty(DifficultyLevel difficultyLevel) {
        return difficultyLevel != null ? difficultyLevel.name() : null;
    }

    private String formatWorkoutTime(TimePeriod timePeriod) {
        if (timePeriod == null) {
            return null;
        }
        if (timePeriod == TimePeriod.None) {
            return "None";
        }
        return timePeriod.name();
    }

    private String formatDiet(DietaryType dietaryType) {
        if (dietaryType == null) {
            return null;
        }
        if (dietaryType == DietaryType.NONE) {
            return "balanced";
        }
        return dietaryType.name();
    }
}
