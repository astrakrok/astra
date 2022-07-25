package com.example.astraapi.service.impl;

import com.example.astraapi.dto.TestVariantDto;
import com.example.astraapi.entity.TestVariantEntity;
import com.example.astraapi.mapper.TestVariantMapper;
import com.example.astraapi.repository.TestVariantRepository;
import com.example.astraapi.service.TestVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestVariantServiceImpl implements TestVariantService {
  private final TestVariantRepository variantRepository;
  private final TestVariantMapper variantMapper;

  @Override
  public void save(Long testId, Collection<TestVariantDto> testVariants) {
    List<TestVariantEntity> variantEntities = testVariants.stream()
        .map(variantMapper::toEntity)
        .collect(Collectors.toList());
    variantRepository.save(testId, variantEntities);
  }
}
