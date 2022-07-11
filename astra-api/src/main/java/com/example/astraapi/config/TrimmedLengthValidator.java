package com.example.astraapi.config;

import com.example.astraapi.annotation.TrimmedLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TrimmedLengthValidator implements ConstraintValidator<TrimmedLength, String> {
  private long min;
  private long max;

  @Override
  public void initialize(TrimmedLength constraintAnnotation) {
    this.min = constraintAnnotation.min();
    this.max = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    int trimmedValueLength = getTrimmedValueLength(value);
    if ( trimmedValueLength>= this.max) {
      return false;
    }
    return trimmedValueLength >= this.min;
  }

  private int getTrimmedValueLength(String value) {
    return value.trim().length();
  }
}
