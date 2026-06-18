package com.wellu.usermanagement.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MealLogEntryResponseDto {

    private UUID id;
    private String mealType;
    private String mealName;
    private List<String> foodItems;
    private Integer calories;
    private Integer protein;
    private Integer carbs;
    private Integer fats;
    private String notes;
}
