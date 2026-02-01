package com.wellu.usermanagement.repository;

import com.wellu.usermanagement.entity.Injury;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InjuryRepository extends JpaRepository<Injury, UUID> {
    List<Injury> findByHealthProfileId(UUID healthProfileId);
}
