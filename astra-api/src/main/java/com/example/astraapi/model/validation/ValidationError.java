package com.example.astraapi.model.validation;

import com.example.astraapi.meta.ValidationErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {
    private ValidationErrorType type;
    private Map<String, Object> details;

    public ValidationError(ValidationErrorType type) {
        this(type, new HashMap<>());
    }
}
