package com.wellu.usermanagement.repository;

import com.wellu.usermanagement.entity.ProgressEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProgressEntryRepository extends JpaRepository<ProgressEntry, UUID> {
    List<ProgressEntry> findByUserProfile_UserId(UUID userId);


    Optional<ProgressEntry>
    findTopByUserProfile_IdOrderByRecordedAtDesc(UUID profileId);

    @Query("""
       SELECT COALESCE(SUM(p.caloriesBurnt), 0)
       FROM ProgressEntry p
       WHERE p.userProfile.id = :profileId
    """)
    double sumCaloriesByProfileId(@Param("profileId") UUID profileId);

    @Query("""
       SELECT COALESCE(SUM(p.workoutCompleted), 0)
       FROM ProgressEntry p
       WHERE p.userProfile.id = :profileId
       AND p.recordedAt >= :startOfWeek
    """)
    double sumWorkoutsThisWeek(@Param("profileId") UUID profileId,
                               @Param("startOfWeek") Instant startOfWeek);
}
