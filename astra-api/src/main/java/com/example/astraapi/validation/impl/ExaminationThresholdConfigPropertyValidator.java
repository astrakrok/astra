package com.example.astraapi.validation.impl;

import com.example.astraapi.meta.ConfigProperty;
import com.example.astraapi.validation.ConfigPropertyValidator;
import org.springframework.stereotype.Component;

@Component
public class ExaminationThresholdConfigPropertyValidator implements ConfigPropertyValidator {
    private final String propertyName = ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName();

    @Override
    public void validate(String value) {
        long parsed = parseValue(value);
        if (parsed < 0 || parsed > 100) {
            throw new IllegalArgumentException("Invalid value for property " + propertyName);
        }
    }

    private Long parseValue(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Property " + propertyName + " is not a number");
        }
    }
}
