package com.wellu.usermanagement.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserProfileUpdateRequest(
        Integer age,
        Double weight,
        Double height
) {
    @JsonCreator
    public UserProfileUpdateRequest(
            @JsonProperty("age") Integer age,
            @JsonProperty("weight") Double weight,
            @JsonProperty("height") Double height
    ) {
        this.age = age;
        this.weight = weight;
        this.height = height;
    }
}