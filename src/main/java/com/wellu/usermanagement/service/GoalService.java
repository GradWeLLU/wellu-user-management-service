package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.GoalRequest;
import com.wellu.usermanagement.dto.response.GoalResponse;
import com.wellu.usermanagement.entity.Goal;
import com.wellu.usermanagement.entity.UserProfile;
import com.wellu.usermanagement.exception.GoalConflictException;
import com.wellu.usermanagement.exception.ProfileNotFoundException;
import com.wellu.usermanagement.mapper.GoalMapper;
import com.wellu.usermanagement.repository.GoalRepository;
import com.wellu.usermanagement.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import jakarta.persistence.*;

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

        validateNoOverlappingGoals(profile.getId(), request);

        Goal goal = goalMapper.toEntity(request);
        goal.setUserProfile(profile);
        goal.setCompleted(false);
        goal.setCurrentValue(0.0);

        Goal saved = goalRepository.save(goal);

        return goalMapper.toResponse(saved);
    }

    private void validateNoOverlappingGoals(UUID profileId, GoalRequest request) {

        List<Goal> overlapping = goalRepository.findOverlappingActiveGoals(
                profileId,
                request.type(),
                request.startDate(),
                request.endDate()
        );

        if (!overlapping.isEmpty()) {
            throw new GoalConflictException(
                    "An active goal of this type already exists in the given date range"
            );
        }
    }
}
