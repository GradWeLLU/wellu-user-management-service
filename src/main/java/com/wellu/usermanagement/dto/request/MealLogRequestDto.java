package com.wellu.usermanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MealLogRequestDto {

    @NotNull
    private LocalDate mealDate;
}
