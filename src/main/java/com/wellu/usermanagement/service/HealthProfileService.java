package com.wellu.usermanagement.service;


import com.wellu.usermanagement.dto.request.*;
import com.wellu.usermanagement.dto.response.HealthProfileResponseDto;
import com.wellu.usermanagement.entity.Allergy;
import com.wellu.usermanagement.entity.HealthProfile;
import com.wellu.usermanagement.entity.Injury;
import com.wellu.usermanagement.entity.Medication;
import com.wellu.usermanagement.exception.ResourceNotFoundException;
import com.wellu.usermanagement.mapper.HealthProfileMapper;
import com.wellu.usermanagement.repository.HealthProfileRepository;
import com.wellu.usermanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class HealthProfileService {


    private final HealthProfileRepository healthProfileRepository;
    private final HealthProfileMapper healthProfileMapper;
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public HealthProfileResponseDto getById(UUID id) {
        HealthProfile profile = healthProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("HealthProfile not found"));
        return healthProfileMapper.toResponse(profile);
    }


    @Transactional(readOnly = true)
    public List<HealthProfileResponseDto> getAll() {
        return healthProfileRepository.findAll()
                .stream()
                .map(healthProfileMapper::toResponse)
                .toList();
    }


    public HealthProfileResponseDto create(HealthProfile profile) {
        HealthProfile saved = healthProfileRepository.save(profile);
        return healthProfileMapper.toResponse(saved);
    }


    public HealthProfileResponseDto update(UUID id, HealthProfile updatedProfile) {
        HealthProfile existing = healthProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("HealthProfile not found"));

        // Simple update logic: replace lists and userProfile if provided
        if (updatedProfile.getInjuries() != null) {
            existing.getInjuries().clear();
            existing.getInjuries().addAll(updatedProfile.getInjuries());
        }
        if (updatedProfile.getAllergies() != null) {
            existing.getAllergies().clear();
            existing.getAllergies().addAll(updatedProfile.getAllergies());
        }
        if (updatedProfile.getMedications() != null) {
            existing.getMedications().clear();
            existing.getMedications().addAll(updatedProfile.getMedications());
        }
// userProfile update skipped for safety


        HealthProfile saved = healthProfileRepository.save(existing);
        return healthProfileMapper.toResponse(saved);
    }


    public void delete(UUID id) {
        if (!healthProfileRepository.existsById(id)) {
            throw new EntityNotFoundException("HealthProfile not found");
        }
        healthProfileRepository.deleteById(id);
    }

    @Transactional
    public HealthProfileResponseDto getMyProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(auth.getName());

        HealthProfile profile = healthProfileRepository.findByUserProfile_User_Id(userId)
                .orElseGet(() -> {
                    HealthProfile newProfile = new HealthProfile();
                    newProfile.setUserProfile(userRepository.getReferenceById(userId).getProfile());
                    return healthProfileRepository.save(newProfile);
                });

        return healthProfileMapper.toResponse(profile);
    }

    public HealthProfileResponseDto updateMyProfile(HealthProfileUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());

        HealthProfile profile = healthProfileRepository.findByUserProfile_User_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("HealthProfile not found"));

        // Simple example: replace lists by IDs (you may fetch entities from repositories)
        // profile.setInjuries(...);
        // profile.setAllergies(...);
        // profile.setMedications(...);

        HealthProfile saved = healthProfileRepository.save(profile);
        return healthProfileMapper.toResponse(saved);
    }

    @Transactional
    public HealthProfileResponseDto patchMyProfile(HealthProfilePatchRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());

        HealthProfile profile = healthProfileRepository.findByUserProfile_User_Id(userId)
                .orElseGet(() -> {
                    HealthProfile newProfile = new HealthProfile();
                    newProfile.setUserProfile(userRepository.getReferenceById(userId).getProfile());
                    return healthProfileRepository.save(newProfile);
                });

        handleAllergies(profile, request);
        handleInjuries(profile, request);
        handleMedications(profile, request);

        healthProfileRepository.save(profile);
        return healthProfileMapper.toResponse(profile);
    }
    private void handleInjuries(HealthProfile profile, HealthProfilePatchRequest request) {
        if (request.injuriesToAdd() != null && !request.injuriesToAdd().isEmpty()) {
            for (CreateInjuryRequest injuryReq : request.injuriesToAdd()) {
                Injury injury = new Injury(
                        injuryReq.name(),
                        injuryReq.description(),
                        injuryReq.severityLevel(),
                        injuryReq.startDate(),
                        injuryReq.endDate(),
                        injuryReq.isChronic()
                );
                profile.addInjury(injury);
            }
        }
        if (request.injuriesToRemove() != null && !request.injuriesToRemove().isEmpty()) {
            profile.getInjuries().removeIf(injury -> request.injuriesToRemove().contains(injury.getId()));
        }
    }
    private void handleAllergies(HealthProfile profile, HealthProfilePatchRequest request) {
        if (request.allergiesToAdd() != null && !request.allergiesToAdd().isEmpty()) {
            for (CreateAllergyRequest allergyReq : request.allergiesToAdd()) {
                Allergy allergy = new Allergy(
                        allergyReq.name(),
                        allergyReq.description(),
                        allergyReq.severityLevel()
                );
                profile.addAllergy(allergy);
            }
        }
        if (request.allergiesToRemove() != null && !request.allergiesToRemove().isEmpty()) {
            profile.getAllergies().removeIf(allergy -> request.allergiesToRemove().contains(allergy.getId()));
        }
    }
    private void handleMedications(HealthProfile profile, HealthProfilePatchRequest request) {
        if (request.medicationsToAdd() != null && !request.medicationsToAdd().isEmpty()) {
            for (CreateMedicationRequest medReq : request.medicationsToAdd()) {
                Medication med = new Medication(
                        medReq.name(),
                        medReq.dosage(),
                        medReq.frequency(),
                        medReq.startDate(),
                        medReq.endDate()
                );
                profile.addMedication(med);
            }
        }
        if (request.medicationsToRemove() != null && !request.medicationsToRemove().isEmpty()) {
            profile.getMedications().removeIf(med -> request.medicationsToRemove().contains(med.getId()));
        }
    }
}