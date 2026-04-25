package com.lhpdesenvolvimentos.jobfast.user.application.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UserRegistrationRequest(
        @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email,
        @NotBlank(message = "Password can not be blank") @Size(min = 6, max = 128, message = "Password must be at least 6 characters") String password
//        @NotBlank(message = "Confirm password can not be blank") String confirmPassword
) {}