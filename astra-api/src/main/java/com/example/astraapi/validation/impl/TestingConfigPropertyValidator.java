package com.example.astraapi.validation.impl;

import com.example.astraapi.validation.ConfigPropertyValidator;

public class TestingConfigPropertyValidator implements ConfigPropertyValidator {
  @Override
  public void validate(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Property cannot be null");
    }
    int length = value.strip().length();
    if (length < 20) {
      throw new IllegalArgumentException("Description must be at least 20 characters long");
    }
  }
}
