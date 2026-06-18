package com.wellu.usermanagement.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MealLogEntryRequestDto {

    @NotBlank
    private String mealType;

    @NotBlank
    private String mealName;

    private List<String> foodItems = new ArrayList<>();

    @Min(0)
    private Integer calories;

    @Min(0)
    private Integer protein;

    @Min(0)
    private Integer carbs;

    @Min(0)
    private Integer fats;

    private String notes;
}
