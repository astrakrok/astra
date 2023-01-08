package com.example.astraapi.exception;

import com.example.astraapi.model.validation.ValidationError;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private final List<ValidationError> errors;

    public ValidationException(ValidationError error) {
        this.errors = List.of(error);
    }

    public ValidationException(List<ValidationError> errors) {
        this.errors = errors;
    }
}
