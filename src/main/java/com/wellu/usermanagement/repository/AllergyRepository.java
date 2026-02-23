package com.wellu.usermanagement.repository;

import com.wellu.usermanagement.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AllergyRepository extends JpaRepository<Allergy, UUID> {
    List<Allergy> findByHealthProfileId(UUID healthProfileId);
}