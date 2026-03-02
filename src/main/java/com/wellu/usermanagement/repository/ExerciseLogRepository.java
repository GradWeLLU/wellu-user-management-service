package com.wellu.usermanagement.repository;

import com.wellu.usermanagement.entity.ExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, UUID> {

    List<ExerciseLog> findByUser_Id(UUID userId);

    Optional<ExerciseLog> findByIdAndUser_Id(UUID id, UUID userId);
}
