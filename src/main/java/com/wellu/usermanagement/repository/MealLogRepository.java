package com.wellu.usermanagement.repository;

import com.wellu.usermanagement.entity.MealLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MealLogRepository extends JpaRepository<MealLog, UUID> {

    List<MealLog> findByUser_Id(UUID userId);

    Optional<MealLog> findByIdAndUser_Id(UUID id, UUID userId);
}
