package com.example.astraapi.validator;

import com.example.astraapi.dto.test.variant.TestVariantDto;
import com.example.astraapi.exception.InvalidCorrectCountException;
import com.example.astraapi.validator.impl.TestVariantsValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TestVariantsValidatorTest {
  @InjectMocks
  private TestVariantsValidator validator;

  @Test
  void shouldThrowExceptionOnEmptyCollection() {
    InvalidCorrectCountException exception = assertThrows(InvalidCorrectCountException.class, () -> validator.validate(Collections.emptyList()));
    assertEquals("Test can have only one correct variant", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionOnCollectionWithAbsentCorrectVariant() {
    List<TestVariantDto> variants = List.of(
        new TestVariantDto(null, null, null, null, null, null, false),
        new TestVariantDto(null, null, null, null, null, null, false),
        new TestVariantDto(null, null, null, null, null, null, false),
        new TestVariantDto(null, null, null, null, null, null, false));
    InvalidCorrectCountException exception = assertThrows(InvalidCorrectCountException.class, () -> validator.validate(variants));
    assertEquals("Test can have only one correct variant", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionOnCollectionWithMultipleCorrectVariants() {
    List<TestVariantDto> variants = List.of(
        new TestVariantDto(null, null, null, null, null, null, true),
        new TestVariantDto(null, null, null, null, null, null, false),
        new TestVariantDto(null, null, null, null, null, null, true),
        new TestVariantDto(null, null, null, null, null, null, false));
    InvalidCorrectCountException exception = assertThrows(InvalidCorrectCountException.class, () -> validator.validate(variants));
    assertEquals("Test can have only one correct variant", exception.getMessage());
  }

  @Test
  void shouldNotThrowExceptionOnCollectionWithOnlyOneCorrectVariant() {
    List<TestVariantDto> variants = List.of(
        new TestVariantDto(null, null, null, null, null, null, false),
        new TestVariantDto(null, null, null, null, null, null, true),
        new TestVariantDto(null, null, null, null, null, null, false),
        new TestVariantDto(null, null, null, null, null, null, false));
    assertDoesNotThrow(() -> validator.validate(variants));
  }
}
