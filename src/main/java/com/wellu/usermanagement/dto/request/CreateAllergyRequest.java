package com.wellu.usermanagement.dto.request;

import com.wellu.usermanagement.enumeration.SeverityLevel;
import jakarta.validation.constraints.NotBlank;

public record CreateAllergyRequest (
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank SeverityLevel severityLevel
        ){
}
