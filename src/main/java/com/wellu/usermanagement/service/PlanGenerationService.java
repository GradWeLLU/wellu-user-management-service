package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.response.WorkoutPlanRequestDTO;
import com.wellu.usermanagement.entity.HealthProfile;
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        UserProfile profile = user.getProfile();
        HealthProfile health = profile != null ? profile.getHealthProfile() : null;

        int age = profile != null ? profile.getAge() : 18;
        double weight = profile != null ? profile.getWeight() : 70;
        double height = profile != null ? profile.getHeight() : 170;
        double bmi = (profile != null && height > 0)
                ? profile.calculateBMI()
                : 20;
        String goal = null;
        if (profile != null && profile.getGoals() != null && !profile.getGoals().isEmpty()) {
            goal = profile.getGoals().getFirst().getGoalType().toString();
        }
        List<String> injuries = List.of();
        if (health != null && health.getInjuries() != null) {
            injuries = health.getInjuries()
                    .stream()
                    .map(injury -> injury.getName())
                    .toList();
        }
        Integer sessionDuration = 60;
        String preferredDifficultyLevel = "medium";
        List<String> preferredEquipment = List.of("bodyweight");
        String experienceLevel = "beginner";

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
}

