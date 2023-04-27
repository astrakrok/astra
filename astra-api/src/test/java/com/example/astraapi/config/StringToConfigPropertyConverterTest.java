package com.example.astraapi.config;

import com.example.astraapi.meta.ConfigProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class StringToConfigPropertyConverterTest {
    @InjectMocks
    private StringToConfigPropertyConverter converter;

    @Test
    void shouldConvertPropertyToEnumValue() {
        ConfigProperty configProperty = converter.convert("examinationThresholdPercentage");

        assertEquals(ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE, configProperty);
    }

    @Test
    void shouldConvertStrippedPropertyToEnumValue() {
        ConfigProperty configProperty = converter.convert("  examinationThresholdPercentage ");

        assertEquals(ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE, configProperty);
    }

    @Test
    void shouldConvertPropertyToEnumValueIgnoreCase() {
        ConfigProperty configProperty = converter.convert("EXAMINAtionThreSHoldPercentAGE");

        assertEquals(ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE, configProperty);
    }

    @Test
    void shouldThrowExceptionOnInvalidValue() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> converter.convert("invalid value"));

        assertEquals("No such property", exception.getMessage());
    }
}
