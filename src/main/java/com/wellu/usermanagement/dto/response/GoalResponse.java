package com.wellu.usermanagement.dto.response;

import com.wellu.usermanagement.enumeration.GoalType;

import java.time.LocalDate;
import java.util.UUID;

public record GoalResponse(
        UUID id,
        GoalType goalType,
        Integer targetValue,
        Integer currentValue,
        LocalDate startDate,
        LocalDate endDate,
        boolean completed) {

}
