package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.AllergyDto;
import com.wellu.usermanagement.entity.Allergy;
import com.wellu.usermanagement.entity.HealthProfile;
import com.wellu.usermanagement.repository.AllergyRepository;
import com.wellu.usermanagement.repository.HealthProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllergyService {

    private final AllergyRepository allergyRepository;
    private final HealthProfileRepository healthProfileRepository;

    public AllergyDto createAllergy(AllergyDto dto) {
        HealthProfile profile = healthProfileRepository.findById(dto.getHealthProfileId())
                .orElseThrow(() -> new RuntimeException("HealthProfile not found"));

        Allergy allergy = new Allergy();
        allergy.setHealthProfile(profile);
        allergy.updateSeverityLevel(dto.getSeverityLevel());
        allergyRepository.save(allergy);

        return toDto(allergy);
    }

    public List<AllergyDto> getAllergies() {
        return allergyRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public AllergyDto getAllergyById(UUID id) {
        return allergyRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Allergy not found"));
    }

    public AllergyDto updateAllergy(UUID id, AllergyDto dto) {
        Allergy allergy = allergyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Allergy not found"));

        if (dto.getSeverityLevel() != null) allergy.updateSeverityLevel(dto.getSeverityLevel());
        if (dto.getName() != null) allergy.setName(dto.getName());
        if (dto.getDescription() != null) allergy.setDescription(dto.getDescription());

        allergyRepository.save(allergy);
        return toDto(allergy);
    }

    public void deleteAllergy(UUID id) {
        allergyRepository.deleteById(id);
    }

    private AllergyDto toDto(Allergy allergy) {
        return new AllergyDto(
                allergy.getId(),
                allergy.getName(),
                allergy.getDescription(),
                allergy.getSeverityLevel(),
                allergy.getHealthProfile().getId()
        );
    }
}