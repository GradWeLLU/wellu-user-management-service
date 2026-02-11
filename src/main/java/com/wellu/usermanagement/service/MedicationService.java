package com.wellu.usermanagement.service;


import com.wellu.usermanagement.dto.request.MedicationCreateRequestDto;
import com.wellu.usermanagement.dto.request.MedicationUpdateRequestDto;
import com.wellu.usermanagement.dto.response.MedicationResponseDto;
import com.wellu.usermanagement.entity.HealthProfile;
import com.wellu.usermanagement.entity.Medication;
import com.wellu.usermanagement.mapper.MedicationMapper;
import com.wellu.usermanagement.repository.HealthProfileRepository;
import com.wellu.usermanagement.repository.MedicationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class MedicationService {


    private final MedicationRepository medicationRepository;
    private final HealthProfileRepository healthProfileRepository;
    private final MedicationMapper medicationMapper;


    public MedicationResponseDto create(MedicationCreateRequestDto request) {
        HealthProfile profile = healthProfileRepository.findById(request.healthProfileId())
                .orElseThrow(() -> new EntityNotFoundException("Health profile not found"));


        Medication medication = medicationMapper.toEntity(request, profile);


        Medication saved = medicationRepository.save(medication);
        return medicationMapper.toResponse(saved);
    }


    @Transactional(readOnly = true)
    public MedicationResponseDto getById(UUID id) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medication not found"));


        return medicationMapper.toResponse(medication);
    }


    @Transactional(readOnly = true)
    public List<MedicationResponseDto> getAllByHealthProfile(UUID healthProfileId) {
        List<Medication> medications = medicationRepository.findAllByHealthProfileId(healthProfileId);
        return medications.stream()
                .map(medicationMapper::toResponse)
                .toList();
    }


    public MedicationResponseDto update(UUID id, MedicationUpdateRequestDto request) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medication not found"));


        medicationMapper.updateEntityFromDto(request, medication);


        Medication updated = medicationRepository.save(medication);
        return medicationMapper.toResponse(updated);
    }


    public void delete(UUID id) {
        if (!medicationRepository.existsById(id)) {
            throw new EntityNotFoundException("Medication not found");
        }
        medicationRepository.deleteById(id);
    }
}