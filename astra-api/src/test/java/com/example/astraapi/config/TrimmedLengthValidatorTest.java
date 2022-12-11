package com.example.astraapi.config;

import com.example.astraapi.annotation.TrimmedLength;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Payload;
import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TrimmedLengthValidatorTest {
  @InjectMocks
  private TrimmedLengthValidator validator;

  @BeforeEach
  void beforeEach() {
    TrimmedLength trimmedLength = new TrimmedLength() {
      @Override
      public Class<? extends Annotation> annotationType() {
        return TrimmedLength.class;
      }

      @Override
      public String message() {
        return "Invalid value";
      }

      @Override
      public long min() {
        return 5;
      }

      @Override
      public long max() {
        return 15;
      }

      @Override
      public Class<?>[] groups() {
        return new Class[0];
      }

      @Override
      public Class<? extends Payload>[] payload() {
        return new Class[0];
      }
    };

    validator.initialize(trimmedLength);
  }

  @Test
  void shouldReturnTrueOnValidValueWithMinLength() {
    assertTrue(validator.isValid("value", null));
  }

  @Test
  void shouldReturnTrueOnValidValueWithMinLengthAndTrailingSpaces() {
    assertTrue(validator.isValid("  value ", null));
  }

  @Test
  void shouldReturnTrueOnValidValue() {
    assertTrue(validator.isValid("ValueValue", null));
  }

  @Test
  void shouldReturnTrueOnValidValueWithTrailingSpaces() {
    assertTrue(validator.isValid("  ValueValue        ", null));
  }

  @Test
  void shouldReturnTrueOnValidValueWithMaxLength() {
    assertTrue(validator.isValid("ValueValueValue", null));
  }

  @Test
  void shouldReturnTrueOnValidValueWithMaxLengthAndTrailingSpaces() {
    assertTrue(validator.isValid(" ValueValueValue  ", null));
  }

  @Test
  void shouldReturnFalseOnInvalidValueLengthWithTrailingSpaces() {
    assertFalse(validator.isValid("   ValueValueValueValue    ", null));
  }

  @Test
  void shouldReturnTrueOnNullValue() {
    assertTrue(validator.isValid(null, null));
  }
}
