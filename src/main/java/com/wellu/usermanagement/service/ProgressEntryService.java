package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.ProgressEntryCreateRequest;
import com.wellu.usermanagement.dto.request.ProgressEntryUpdateRequest;
import com.wellu.usermanagement.dto.response.ProgressEntryResponse;
import com.wellu.usermanagement.entity.ProgressEntry;
import com.wellu.usermanagement.entity.UserProfile;
import com.wellu.usermanagement.exception.AuthenticationException;
import com.wellu.usermanagement.exception.ProfileNotFoundException;
import com.wellu.usermanagement.exception.ProgressEntryException;
import com.wellu.usermanagement.mapper.ProgressEntryMapper;
import com.wellu.usermanagement.repository.ProgressEntryRepository;
import com.wellu.usermanagement.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ProgressEntryService {
    UserProfileRepository userProfileRepository;
    ProgressEntryRepository progressEntryRepository;
    ProgressEntryMapper progressEntryMapper;

    public ProgressEntryService(UserProfileRepository userProfileRepository, ProgressEntryRepository progressEntryRepository,ProgressEntryMapper progressEntryMapper) {
        this.userProfileRepository = userProfileRepository;
        this.progressEntryRepository = progressEntryRepository;
        this.progressEntryMapper = progressEntryMapper;
    }

    public ProgressEntryResponse create(UUID userId, ProgressEntryCreateRequest request) {

        progressEntryCreateValidation(request);

        UserProfile userProfile = userProfileRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));

        ProgressEntry entry = progressEntryMapper.toEntity(request);

        entry.setRecordedAt(Instant.now());
        entry.setUserProfile(userProfile);

        progressEntryRepository.save(entry);

        return progressEntryMapper.toResponse(entry);
    }



    public List<ProgressEntryResponse> getAll(UUID userId) {

        return progressEntryRepository
                .findByUserProfile_UserId(userId)
                .stream()
                .map(progressEntryMapper::toResponse)
                .toList();
    }

    public void delete(UUID userId, UUID id) {

        ProgressEntry entry = progressEntryRepository.findById(id)
                .orElseThrow(() -> new ProgressEntryException("Progress entry not found"));

        if (!entry.getUserProfile().getUser().getId().equals(userId)) {
            throw new AuthenticationException("You cannot delete this entry");
        }

        progressEntryRepository.delete(entry);
    }

    public ProgressEntryResponse update(UUID userId, UUID id, ProgressEntryUpdateRequest request) {
        ProgressEntry entry = progressEntryRepository.findById(id)
                .orElseThrow(() -> new ProgressEntryException("Progress entry not found"));

        if (!entry.getUserProfile().getUser().getId().equals(userId)) {
            throw new AuthenticationException("You cannot delete this entry");
        }

        progressEntryUpdateValidation(request);
        progressEntryMapper.updateFromDto(request, entry);
        progressEntryRepository.save(entry);

        return progressEntryMapper.toResponse(entry);
    }

    private void progressEntryCreateValidation(ProgressEntryCreateRequest request) {
        if (request.weight() == null || request.weight() <= 0) {
            throw new ProgressEntryException("Weight must be greater than 0");
        }

        if (request.caloriesBurnt() != null && request.caloriesBurnt() < 0) {
            throw new ProgressEntryException("Calories burnt cannot be negative");
        }

        if (request.workoutCompleted() != null && request.workoutCompleted() < 0) {
            throw new ProgressEntryException("Workout count cannot be negative");
        }
    }

    private void progressEntryUpdateValidation(ProgressEntryUpdateRequest request) {

        if (request.weight() != null && request.weight() <= 0) {
            throw new ProgressEntryException("Weight must be greater than 0");
        }

        if (request.caloriesBurnt() != null && request.caloriesBurnt() < 0) {
            throw new ProgressEntryException("Calories burnt cannot be negative");
        }

        if (request.workoutCompleted() != null && request.workoutCompleted() < 0) {
            throw new ProgressEntryException("Workout count cannot be negative");
        }
    }
}
