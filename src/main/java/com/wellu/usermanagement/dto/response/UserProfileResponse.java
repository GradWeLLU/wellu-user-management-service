package com.wellu.usermanagement.dto.response;

import com.wellu.usermanagement.enumeration.UserRole;
import com.wellu.usermanagement.enumeration.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record UserProfileResponse (
        UUID id,
        String email,
        String firstName,
        String lastName,

        // Profile fields
        Integer age,
        Double weight,
        Double height,
        Double bmi
){ }
