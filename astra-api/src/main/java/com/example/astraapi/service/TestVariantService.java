package com.example.astraapi.service;

import com.example.astraapi.dto.test.variant.TestVariantDto;

import java.util.Collection;

public interface TestVariantService {
  void save(Long testId, Collection<TestVariantDto> testVariants);

  void update(Long testId, Collection<TestVariantDto> testVariants);
}
