package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.GoalRequest;
import com.wellu.usermanagement.dto.request.GoalUpdateRequest;
import com.wellu.usermanagement.dto.response.GoalResponse;
import com.wellu.usermanagement.entity.Goal;
import com.wellu.usermanagement.entity.UserProfile;
import com.wellu.usermanagement.exception.GoalNotFoundException;
import com.wellu.usermanagement.exception.InvalidGoalDateException;
import com.wellu.usermanagement.exception.ProfileNotFoundException;
import com.wellu.usermanagement.mapper.GoalMapper;
import com.wellu.usermanagement.repository.GoalRepository;
import com.wellu.usermanagement.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;
    private final UserProfileRepository userProfileRepository;

    public GoalService(GoalRepository goalRepository, GoalMapper goalMapper, UserProfileRepository userProfileRepository) {
        this.goalRepository = goalRepository;
        this.goalMapper = goalMapper;
        this.userProfileRepository = userProfileRepository;
    }


    public List<GoalResponse> getGoals(UUID userId) {

        UserProfile profile = userProfileRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));

        List<Goal> goals = goalRepository
                .findByUserProfileId(profile.getId());



        return goalMapper.toResponseList(goals);
    }

    @Transactional
    public GoalResponse createGoal(UUID userId, GoalRequest request) {

        UserProfile profile = userProfileRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));

        validateDates(request.startDate(), request.endDate());

        Goal goal = goalMapper.toEntity(request);
        goal.setUserProfile(profile);
        goal.setCompleted(false);
        goal.setCurrentValue(0.0);

        Goal saved = goalRepository.save(goal);

        return goalMapper.toResponse(saved);
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        LocalDate maxAllowedStart = LocalDate.now().plusYears(1);

        if (startDate.isBefore(today)) {
            throw new InvalidGoalDateException("Start date cannot be in the past");
        }

        if (startDate.isAfter(maxAllowedStart)) {
            throw new InvalidGoalDateException("Start date cannot be more than 1 year from today");
        }

        if (endDate.isAfter(startDate.plusYears(1))) {
            throw new InvalidGoalDateException("Goal duration cannot exceed 1 year");
        }

        if (startDate.isAfter(endDate)) {
            throw new InvalidGoalDateException("Start date cannot be after end date");
        }

    }

    public GoalResponse updateGoal(UUID goalId, UUID userId, GoalUpdateRequest request) {

        //checks if goal exists, and whether goals belongs to that user
        Goal goal = goalRepository
                .findByIdAndUserProfile_UserId(goalId, userId)
                .orElseThrow(() -> new GoalNotFoundException("Goal not found"));

        validateDates(request.startDate(), request.endDate());

        goal.setTargetValue(request.targetValue());
        goal.setStartDate(request.startDate());
        goal.setEndDate(request.endDate());

        return goalMapper.toResponse(goalRepository.save(goal));
    }
}
