package com.wellu.usermanagement.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "meal_entries")
public class MealLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String mealType;

    @Column(nullable = false)
    private String mealName;

    @ElementCollection
    @CollectionTable(name = "meal_entry_food_items", joinColumns = @JoinColumn(name = "meal_entry_id"))
    @Column(name = "food_item", nullable = false)
    private List<String> foodItems = new ArrayList<>();

    @Min(0)
    private Integer calories;

    @Min(0)
    private Integer protein;

    @Min(0)
    private Integer carbs;

    @Min(0)
    private Integer fats;

    @Column(length = 1000)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meal_log_id", nullable = false)
    private MealLog mealLog;
}
