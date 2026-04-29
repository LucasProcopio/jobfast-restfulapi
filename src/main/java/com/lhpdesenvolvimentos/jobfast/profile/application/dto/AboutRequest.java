package com.lhpdesenvolvimentos.jobfast.profile.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AboutRequest(
    @NotBlank(message = "Zip code is required") String zipCode,
    @NotBlank(message = "Address is required") String address,
    @NotBlank(message = "City name is required") String cityName,
    @NotBlank(message = "State is required") String state,
    boolean disability,
    @NotBlank(message= "Gender is required")
    @NotNull(message = "Gender number is required") 
    @Min(value = 0, message = "Invalid gender number") 
    @Max(value = 4, message = "Invalid gender number") // 0 = Male, 1 = Female, 2 = Other, 3 = Prefer not to say, 4 = Non-binary
    Integer genderNum
) {
    
}
