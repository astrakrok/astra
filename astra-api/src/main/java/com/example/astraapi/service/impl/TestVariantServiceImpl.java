package com.example.astraapi.service.impl;

import com.example.astraapi.dto.test.variant.TestVariantDto;
import com.example.astraapi.entity.TestVariantEntity;
import com.example.astraapi.mapper.TestVariantMapper;
import com.example.astraapi.model.Change;
import com.example.astraapi.repository.TestVariantRepository;
import com.example.astraapi.service.ChangeService;
import com.example.astraapi.service.TestVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestVariantServiceImpl implements TestVariantService {
  private final ChangeService changeService;
  private final TestVariantRepository variantRepository;
  private final TestVariantMapper variantMapper;

  @Override
  public void save(Long testId, Collection<TestVariantDto> testVariants) {
    if (testVariants.isEmpty()) {
      return;
    }
    List<TestVariantEntity> variantEntities = testVariants.stream()
        .map(variantMapper::toEntity)
        .collect(Collectors.toList());
    variantRepository.save(testId, variantEntities);
  }

  @Override
  @Transactional
  public void update(Long testId, Collection<TestVariantDto> testVariants) {
    Set<Long> oldVariantsIds = variantRepository.getVariantsIdsByTestId(testId);
    Set<Long> existentVariantsIds = testVariants.stream()
        .map(TestVariantDto::getId)
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
    Change<Long> change = changeService.getChange(oldVariantsIds, existentVariantsIds);
    if (!change.getRemoved().isEmpty()) {
      variantRepository.delete(testId, change.getRemoved());
    }
    testVariants.stream()
        .map(variantMapper::toEntity)
        .forEach(variant -> saveOrUpdate(testId, variant));
  }

  private void saveOrUpdate(Long testId, TestVariantEntity variant) {
    if (variant.getId() == null) {
      variantRepository.save(testId, Set.of(variant));
    } else {
      variantRepository.update(testId, variant);
    }
  }
}
