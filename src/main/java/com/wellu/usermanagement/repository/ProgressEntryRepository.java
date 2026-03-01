package com.wellu.usermanagement.repository;

import com.wellu.usermanagement.entity.ProgressEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface ProgressEntryRepository extends JpaRepository<ProgressEntry, UUID> {
    List<ProgressEntry> findByUserProfile_UserId(UUID userId);
}
