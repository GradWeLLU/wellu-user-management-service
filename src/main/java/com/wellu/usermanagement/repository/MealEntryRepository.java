package com.wellu.usermanagement.repository;

import com.wellu.usermanagement.entity.MealLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MealEntryRepository extends JpaRepository<MealLogEntry, UUID> {

    List<MealLogEntry> findByMealLogId(UUID mealLogId);
}
