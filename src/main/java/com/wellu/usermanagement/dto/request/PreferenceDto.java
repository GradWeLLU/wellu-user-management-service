package com.wellu.usermanagement.dto.request;

import com.wellu.usermanagement.enumeration.DifficultyLevel;
import com.wellu.usermanagement.enumeration.DietaryType;
import com.wellu.usermanagement.enumeration.TimePeriod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceDto {
    private UUID id;
    private Integer preferredWorkoutDuration;
    private DifficultyLevel preferredDifficulty;
    private List<TimePeriod> preferredTimePeriods;
    private List<String> preferredEquipment;
    private List<DietaryType> preferredDietaryType;
    private List<String> dislikedFoods;
    private List<String> preferredCuisines;
}