package com.wellu.usermanagement.repository;

import com.wellu.usermanagement.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PreferenceRepository extends JpaRepository<Preference, UUID> {
}