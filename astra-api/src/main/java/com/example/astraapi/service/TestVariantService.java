package com.example.astraapi.service;

import com.example.astraapi.dto.TestVariantDto;

import java.util.Collection;

public interface TestVariantService {
  void save(Long testId, Collection<TestVariantDto> testVariants);
}
