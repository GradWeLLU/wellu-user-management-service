package com.wellu.usermanagement.dto.request;

import java.time.LocalDate;

public record GoalUpdateRequest(
         Double targetValue,
         LocalDate startDate,
        LocalDate endDate

) {
}
