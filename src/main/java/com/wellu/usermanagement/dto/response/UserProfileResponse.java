package com.wellu.usermanagement.dto.response;

import java.util.UUID;

public record UserProfileResponse (
        UUID id,
        String email,
        String firstName,
        String lastName,
//        UserRole role,
//        UserStatus status,
//        Instant createdAt,

        // Profile fields
        Integer age,
        Double weight,
        Double height,
        Double bmi
){ }
