package com.wellu.usermanagement.dto.request;

import com.wellu.usermanagement.enumeration.SeverityLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllergyDto {
    private UUID id;
    private String name;
    private String description;
    private SeverityLevel severityLevel;
    private UUID healthProfileId;
}