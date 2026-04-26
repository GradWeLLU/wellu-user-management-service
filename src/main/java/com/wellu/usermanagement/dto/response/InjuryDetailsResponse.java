package com.wellu.usermanagement.dto.response;

import java.time.Instant;
import java.util.List;

public record InjuryDetailsResponse(
        boolean hasInjury,
        List<String> areas,
        Instant startDate,
        Instant endDate,
        boolean stillFeelIt
) {
}
