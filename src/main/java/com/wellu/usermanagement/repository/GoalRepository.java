package com.wellu.usermanagement.repository;

import com.wellu.usermanagement.entity.Goal;
import com.wellu.usermanagement.enumeration.GoalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoalRepository extends JpaRepository<Goal, UUID> {
    List<Goal> findByUserProfileId(UUID profileId);

    @Query("""
        SELECT g FROM Goal g
        WHERE g.userProfile.id = :profileId
        AND g.goalType = :type
        AND g.isCompleted = false
        AND (
                :startDate <= g.endDate
            AND :endDate >= g.startDate
        )
    """)
    List<Goal> findOverlappingActiveGoals(
            UUID profileId,
            GoalType type,
            LocalDate startDate,
            LocalDate endDate
    );

    Optional<Goal> findByIdAndUserProfile_UserId(UUID goalId, UUID userId);
}
