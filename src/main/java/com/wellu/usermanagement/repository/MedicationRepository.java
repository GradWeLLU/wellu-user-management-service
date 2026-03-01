package com.wellu.usermanagement.repository;

import com.wellu.usermanagement.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MedicationRepository extends JpaRepository<Medication, UUID> {
    List<Medication> findAllByHealthProfileId(UUID healthProfileId);
}
