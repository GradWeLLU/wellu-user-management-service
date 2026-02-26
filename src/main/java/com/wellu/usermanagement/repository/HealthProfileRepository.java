package com.wellu.usermanagement.repository;

import com.wellu.usermanagement.entity.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HealthProfileRepository extends JpaRepository<HealthProfile, UUID> {
    Optional<HealthProfile> findByUserProfileId(UUID userProfileId);
    Optional<HealthProfile> findByUserProfile_User_Id(UUID userId);
}
