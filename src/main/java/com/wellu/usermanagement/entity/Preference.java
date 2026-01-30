package com.wellu.usermanagement.entity;

import com.wellu.usermanagement.enumeration.DietaryType;
import com.wellu.usermanagement.enumeration.DifficultyLevel;
import com.wellu.usermanagement.enumeration.TimePeriod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "preferences")
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    @Min(5)
    private Integer preferredWorkoutDuration;

    @Column
    @Enumerated(EnumType.STRING)
    private DifficultyLevel preferredDifficulty;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "preference_time_periods",
            joinColumns = @JoinColumn(name = "preference_id")
    )
    @Column(name = "time_period")
    private List<TimePeriod> preferredTimePeriods = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "preference_equipment",
            joinColumns = @JoinColumn(name = "preference_id")
    )
    @Column(name = "equipment")
    private List<String> preferredEquipment = new ArrayList<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "preference_dietary_types",
            joinColumns = @JoinColumn(name = "preference_id")
    )
    @Column(name = "dietary_type")
    private List<DietaryType> preferredDietaryType = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "preference_disliked_foods",
            joinColumns = @JoinColumn(name = "preference_id")
    )
    @Column(name = "food")
    private List<String> dislikedFoods = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "preference_cuisines",
            joinColumns = @JoinColumn(name = "preference_id")
    )
    @Column(name = "cuisine")
    private List<String> preferredCuisines = new ArrayList<>();

    public void setPreferredWorkoutDuration(Integer duration){
        if(duration != null && duration < 5) throw new IllegalArgumentException(("Duration must be longer than 5 minutes!"));
        this.preferredWorkoutDuration = duration;
    }

    public void setPreferredDifficulty(DifficultyLevel level){
        this.preferredDifficulty = level;
    }
    public void addPreferredTime(TimePeriod time){
        if(!preferredTimePeriods.contains(time))
            preferredTimePeriods.add(time);
    }
    public void addPreferredCuisine(String cuisine){
        if(!preferredCuisines.contains(cuisine))
            preferredCuisines.add(cuisine);
    }
    public void addDislikedFood(String food){
        if(!dislikedFoods.contains(food))
            dislikedFoods.add(food);
    }

}
