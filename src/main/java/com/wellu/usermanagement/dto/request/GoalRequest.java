package com.wellu.usermanagement.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wellu.usermanagement.enumeration.GoalType;

import java.time.LocalDate;

public record GoalRequest(
         GoalType type,
         Integer targetValue,
         @JsonFormat(pattern = "yyyy-MM-dd")
         LocalDate startDate,
         @JsonFormat(pattern = "yyyy-MM-dd")
         LocalDate endDate
) {
}
