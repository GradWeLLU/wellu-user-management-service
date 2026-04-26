package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.response.NutritionPlanRequestDTO;
import com.wellu.usermanagement.dto.response.WorkoutPlanRequestDTO;
import com.wellu.usermanagement.entity.HealthProfile;
import com.wellu.usermanagement.entity.Preference;
import com.wellu.usermanagement.entity.User;
import com.wellu.usermanagement.entity.UserProfile;
import com.wellu.usermanagement.exception.UserNotFoundException;
import com.wellu.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanGenerationService {
    private final UserRepository userRepository;

    public ResponseEntity<WorkoutPlanRequestDTO> buildWorkoutPlanRequestDTO(UUID userId){
        User user = getUserById(userId);
        UserProfile profile = user.getProfile();
        HealthProfile health = profile != null ? profile.getHealthProfile() : null;
        Preference preference = profile != null ? profile.getPreference() : null;

        int age = profile != null ? profile.getAge() : 18;
        double weight = profile != null ? profile.getWeight() : 70;
        double height = profile != null ? profile.getHeight() : 170;
        double bmi = (profile != null && height > 0)
                ? profile.calculateBMI()
                : 20;
        String goal = extractGoal(profile);
        List<String> injuries = extractInjuries(health);
        Integer sessionDuration = preference != null && preference.getPreferredWorkoutDuration() != null
                ? preference.getPreferredWorkoutDuration()
                : 60;
        String preferredDifficultyLevel = preference != null && preference.getPreferredDifficulty() != null
                ? preference.getPreferredDifficulty().name()
                : "INTERMEDIATE";
        List<String> preferredEquipment = List.of("bodyweight");
        String experienceLevel = profile != null && profile.getFitnessLevel() != null
                ? profile.getFitnessLevel()
                : "BEGINNER";

        WorkoutPlanRequestDTO details = new WorkoutPlanRequestDTO(
                user.getId(),
                goal,
                age,
                weight,
                height,
                bmi,
                sessionDuration,
                preferredDifficultyLevel,
                preferredEquipment,
                experienceLevel,
                injuries
        );
        return ResponseEntity.ok(details);
    }

    public ResponseEntity<NutritionPlanRequestDTO> buildNutritionPlanRequestDTO(UUID userId) {
        User user = getUserById(userId);
        UserProfile profile = user.getProfile();
        Preference preference = profile != null ? profile.getPreference() : null;

        Integer age = profile != null ? profile.getAge() : 18;
        Double weight = profile != null ? profile.getWeight() : 70.0;
        Double height = profile != null ? profile.getHeight() : 170.0;
        String goal = extractGoal(profile);
        String gender = profile != null && profile.getGender() != null ? profile.getGender() : "Not specified";
        String activityLevel = profile != null && profile.getFitnessLevel() != null ? profile.getFitnessLevel() : "moderate";
        String budget = "moderate";
        String diet = preference != null && !preference.getPreferredDietaryType().isEmpty()
                ? formatDiet(preference.getPreferredDietaryType().getFirst())
                : "balanced";
        int mealsPerDay = 3;

        NutritionPlanRequestDTO details = new NutritionPlanRequestDTO(
                user.getId(),
                goal,
                weight,
                height,
                age,
                gender,
                activityLevel,
                budget,
                diet,
                mealsPerDay
        );
        return ResponseEntity.ok(details);
    }

    private User getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return user;
    }

    private String extractGoal(UserProfile profile) {
        if (profile != null && profile.getMainGoal() != null && !profile.getMainGoal().isBlank()) {
            return profile.getMainGoal();
        }

        String goal = null;
        if (profile != null && profile.getGoals() != null && !profile.getGoals().isEmpty()) {
            goal = profile.getGoals().getFirst().getGoalType().toString();
        }
        return goal;
    }

    private List<String> extractInjuries(HealthProfile health) {
        List<String> injuries = List.of();
        if (health != null && health.getInjuries() != null) {
            injuries = health.getInjuries()
                    .stream()
                    .map(injury -> injury.getName())
                    .toList();
        }
        return injuries;
    }

    private String formatDiet(com.wellu.usermanagement.enumeration.DietaryType dietaryType) {
        return dietaryType == com.wellu.usermanagement.enumeration.DietaryType.NONE
                ? "balanced"
                : dietaryType.name();
    }
}

