package com.wellu.usermanagement.service;


import com.wellu.usermanagement.dto.request.HealthProfileUpdateRequest;
import com.wellu.usermanagement.dto.response.HealthProfileResponseDto;
import com.wellu.usermanagement.entity.HealthProfile;
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
}