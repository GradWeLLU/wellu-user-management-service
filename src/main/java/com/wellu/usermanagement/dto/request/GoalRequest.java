package com.wellu.usermanagement.dto.request;

import com.wellu.usermanagement.enumeration.GoalType;

import java.time.LocalDate;

public record GoalRequest(
         GoalType type,
         Integer targetValue,
         LocalDate startDate,
         LocalDate endDate
) {

}
