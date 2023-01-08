package com.example.astraapi.exception;

import com.example.astraapi.meta.ValidationErrorType;

public class ImportException extends RuntimeException {
    private final ValidationErrorType type;

    public ImportException(ValidationErrorType type) {
        this.type = type;
    }

    public ImportException(ValidationErrorType type, Throwable cause) {
        super(cause);
        this.type = type;
    }
}
