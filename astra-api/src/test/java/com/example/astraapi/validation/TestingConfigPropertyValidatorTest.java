package com.example.astraapi.validation;

import com.example.astraapi.validation.impl.TestingConfigPropertyValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestingConfigPropertyValidatorTest {
  @InjectMocks
  private TestingConfigPropertyValidator validator;

  @Test
  void shouldThrowExceptionOnNullValue() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(null));
    assertEquals("Property cannot be null", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionOnInvalidLength() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate("ValueValueValueDone"));
    assertEquals("Description must be at least 20 characters long", exception.getMessage());
  }

  @Test
  void shouldStringValueThrowExceptionOnInvalidLength() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(" ValueValueValueDone    "));
    assertEquals("Description must be at least 20 characters long", exception.getMessage());
  }

  @Test
  void shouldNotThrowExceptionOnValidValue() {
    assertDoesNotThrow(() -> validator.validate("ValueValueValueValue"));
  }
}
