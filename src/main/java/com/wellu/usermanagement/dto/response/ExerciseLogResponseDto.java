package com.wellu.usermanagement.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseLogResponseDto {
    private UUID id;
    private LocalDate workoutDate;
    private List<ExerciseLogEntryResponseDto> entries;
}