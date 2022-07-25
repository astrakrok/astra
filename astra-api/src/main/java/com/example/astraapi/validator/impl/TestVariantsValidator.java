package com.example.astraapi.validator.impl;

import com.example.astraapi.dto.TestVariantDto;
import com.example.astraapi.exception.InvalidCorrectCountException;
import com.example.astraapi.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class TestVariantsValidator implements Validator<Collection<TestVariantDto>> {
  @Override
  public void validate(Collection<TestVariantDto> testVariants) {
    if (getCorrectVariants(testVariants) != 1) {
      throw new InvalidCorrectCountException("Test can have only one correct variant");
    }
  }

  private long getCorrectVariants(Collection<TestVariantDto> testVariants) {
    return testVariants.stream()
        .filter(TestVariantDto::isCorrect)
        .count();
  }
}
