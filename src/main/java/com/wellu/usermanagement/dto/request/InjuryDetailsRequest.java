package com.wellu.usermanagement.dto.request;

import java.time.Instant;
import java.util.List;

public record InjuryDetailsRequest(
        Boolean hasInjury,
        List<String> areas,
        Instant startDate,
        Instant endDate,
        Boolean stillFeelIt
) {
}
