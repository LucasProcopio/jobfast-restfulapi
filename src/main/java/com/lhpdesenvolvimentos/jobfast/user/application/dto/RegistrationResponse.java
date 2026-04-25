package com.lhpdesenvolvimentos.jobfast.user.application.dto;

public class RegistrationResponse {
    private String message;

    protected RegistrationResponse() {
        // Default constructor for deserialization
    }

    public RegistrationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
