package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.PreferenceDto;
import com.wellu.usermanagement.entity.Preference;
import com.wellu.usermanagement.repository.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;

    public PreferenceDto createPreference(PreferenceDto dto) {
        Preference preference = new Preference();
        updateEntityFromDto(preference, dto);

        preferenceRepository.save(preference);
        return toDto(preference);
    }

    public List<PreferenceDto> getPreferences() {
        return preferenceRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PreferenceDto getPreferenceById(UUID id) {
        return preferenceRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Preference not found"));
    }

    public PreferenceDto updatePreference(UUID id, PreferenceDto dto) {
        Preference preference = preferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Preference not found"));

        updateEntityFromDto(preference, dto);
        preferenceRepository.save(preference);

        return toDto(preference);
    }

    public void deletePreference(UUID id) {
        preferenceRepository.deleteById(id);
    }

    private void updateEntityFromDto(Preference preference, PreferenceDto dto) {
        if (dto.getPreferredWorkoutDuration() != null)
            preference.setPreferredWorkoutDuration(dto.getPreferredWorkoutDuration());

        if (dto.getPreferredDifficulty() != null)
            preference.setPreferredDifficulty(dto.getPreferredDifficulty());

        if (dto.getPreferredTimePeriods() != null)
            dto.getPreferredTimePeriods().forEach(preference::addPreferredTime);

        if (dto.getPreferredCuisines() != null)
            dto.getPreferredCuisines().forEach(preference::addPreferredCuisine);

        if (dto.getDislikedFoods() != null)
            dto.getDislikedFoods().forEach(preference::addDislikedFood);

        if (dto.getPreferredEquipment() != null) {
            preference.getPreferredEquipment().clear();
            preference.getPreferredEquipment().addAll(dto.getPreferredEquipment());
        }

        if (dto.getPreferredDietaryType() != null) {
            preference.getPreferredDietaryType().clear();
            preference.getPreferredDietaryType().addAll(dto.getPreferredDietaryType());
        }
    }

    private PreferenceDto toDto(Preference preference) {
        return new PreferenceDto(
                preference.getId(),
                preference.getPreferredWorkoutDuration(),
                preference.getPreferredDifficulty(),
                preference.getPreferredTimePeriods(),
                preference.getPreferredEquipment(),
                preference.getPreferredDietaryType(),
                preference.getDislikedFoods(),
                preference.getPreferredCuisines()
        );
    }
}