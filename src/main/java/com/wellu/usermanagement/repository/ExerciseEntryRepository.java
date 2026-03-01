package com.wellu.usermanagement.repository;

import com.wellu.usermanagement.entity.ExerciseLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExerciseEntryRepository extends JpaRepository<ExerciseLogEntry, UUID> {
    List<ExerciseLogEntry> findByExerciseLogId(UUID exerciseLogId);
}
