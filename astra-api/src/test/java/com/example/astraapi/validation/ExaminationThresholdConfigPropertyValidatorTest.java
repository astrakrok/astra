package com.example.astraapi.validation;

import com.example.astraapi.validation.impl.ExaminationThresholdConfigPropertyValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ExaminationThresholdConfigPropertyValidatorTest {
    @InjectMocks
    private ExaminationThresholdConfigPropertyValidator validator;

    @Test
    void shouldThrowExceptionOnNotNumberValue() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate("not a number"));
        assertEquals("Property examinationThresholdPercentage is not a number", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionOnNullValue() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(null));
        assertEquals("Property examinationThresholdPercentage is not a number", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionOnNegativeValue() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate("-1"));
        assertEquals("Invalid value for property examinationThresholdPercentage", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionOnExceededValue() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate("101"));
        assertEquals("Invalid value for property examinationThresholdPercentage", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionOnMinimumPercent() {
        assertDoesNotThrow(() -> validator.validate("0"));
    }

    @Test
    void shouldNotThrowExceptionOnMaximumPercent() {
        assertDoesNotThrow(() -> validator.validate("100"));
    }
}
