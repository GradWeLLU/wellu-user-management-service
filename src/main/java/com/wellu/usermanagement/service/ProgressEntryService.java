package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.ProgressEntryCreateRequest;
import com.wellu.usermanagement.dto.request.ProgressEntryUpdateRequest;
import com.wellu.usermanagement.dto.response.ProgressEntryResponse;
import com.wellu.usermanagement.entity.ProgressEntry;
import com.wellu.usermanagement.entity.UserProfile;
import com.wellu.usermanagement.enumeration.GoalType;
import com.wellu.usermanagement.exception.AuthenticationException;
import com.wellu.usermanagement.exception.ProfileNotFoundException;
import com.wellu.usermanagement.exception.ProgressEntryException;
import com.wellu.usermanagement.mapper.ProgressEntryMapper;
import com.wellu.usermanagement.repository.ProgressEntryRepository;
import com.wellu.usermanagement.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class ProgressEntryService {
    UserProfileRepository userProfileRepository;
    ProgressEntryRepository progressEntryRepository;
    ProgressEntryMapper progressEntryMapper;
    GoalService goalService;

    public ProgressEntryService(UserProfileRepository userProfileRepository, ProgressEntryRepository progressEntryRepository,ProgressEntryMapper progressEntryMapper, GoalService goalService) {
        this.userProfileRepository = userProfileRepository;
        this.progressEntryRepository = progressEntryRepository;
        this.progressEntryMapper = progressEntryMapper;
        this.goalService = goalService;
    }

    @Transactional
    public ProgressEntryResponse create(UUID userId, ProgressEntryCreateRequest request) {

        progressEntryCreateValidation(request);

        UserProfile userProfile = userProfileRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
        UUID profileId = userProfile.getId();
        ProgressEntry entry = progressEntryMapper.toEntity(request);

        entry.setRecordedAt(Instant.now());
        entry.setUserProfile(userProfile);

        progressEntryRepository.save(entry);
        recalculateGoals(profileId);

        return progressEntryMapper.toResponse(entry);
    }

    public List<ProgressEntryResponse> getAll(UUID userId) {

        return progressEntryRepository
                .findByUserProfile_UserId(userId)
                .stream()
                .map(progressEntryMapper::toResponse)
                .toList();
    }

    @Transactional
    public void delete(UUID userId, UUID id) {

        ProgressEntry entry = progressEntryRepository.findById(id)
                .orElseThrow(() -> new ProgressEntryException("Progress entry not found"));

        if (!entry.getUserProfile().getUser().getId().equals(userId)) {
            throw new AuthenticationException("You cannot delete this entry");
        }

        progressEntryRepository.delete(entry);
        recalculateGoals(entry.getUserProfile().getId());
    }

    @Transactional
    public ProgressEntryResponse update(UUID userId, UUID id, ProgressEntryUpdateRequest request) {
        ProgressEntry entry = progressEntryRepository.findById(id)
                .orElseThrow(() -> new ProgressEntryException("Progress entry not found"));

        if (!entry.getUserProfile().getUser().getId().equals(userId)) {
            throw new AuthenticationException("You cannot delete this entry");
        }

        progressEntryUpdateValidation(request);
        progressEntryMapper.updateFromDto(request, entry);

        recalculateGoals(entry.getUserProfile().getId());

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

    @Transactional
    protected void recalculateGoals(UUID profileId) {

        updateWeightGoal(profileId);
        updateCaloriesGoal(profileId);
        updateWorkoutPerWeekGoal(profileId);
    }

    private void updateWeightGoal(UUID profileId) {

        Double latestWeight = progressEntryRepository
                .findTopByUserProfile_IdOrderByRecordedAtDesc(profileId)
                .map(ProgressEntry::getWeight)
                .orElse(null);

        if (latestWeight != null) {
            goalService.updateGoalProgress(profileId, GoalType.WEIGHT, latestWeight);
        }
    }

    private void updateCaloriesGoal(UUID profileId) {

        double total = progressEntryRepository.sumCaloriesByProfileId(profileId);

        goalService.updateGoalProgress(profileId, GoalType.CALORIES, total);
    }

    private void updateWorkoutPerWeekGoal(UUID profileId) {

        Instant startOfWeek = LocalDate.now()
                .with(DayOfWeek.MONDAY)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        double total = progressEntryRepository
                .sumWorkoutsThisWeek(profileId, startOfWeek);

        goalService.updateGoalProgress(profileId, GoalType.WORKOUT_PER_WEEK, total);
    }
}
