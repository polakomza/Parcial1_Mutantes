package com.example.Mutants.exceptions;

import com.example.Mutants.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDnaException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidDnaException(InvalidDnaException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

