package com.example.Mutants.dto;

public class ErrorResponseDTO {

    private String message;

    public ErrorResponseDTO(String message) {
        this.message = message;
    }

    // Getters y Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

