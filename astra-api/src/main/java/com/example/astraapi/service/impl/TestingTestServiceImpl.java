package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.test.RequestTestingTestDto;
import com.example.astraapi.entity.TestingTestEntity;
import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.mapper.TestingTestMapper;
import com.example.astraapi.meta.TestStatus;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.repository.TestRepository;
import com.example.astraapi.repository.TestingTestRepository;
import com.example.astraapi.service.TestingTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class TestingTestServiceImpl implements TestingTestService {
  private final TestingTestRepository testingTestRepository;
  private final TestingTestMapper testingTestMapper;
  private final TestRepository testRepository;

  @Override
  @Transactional
  public IdDto save(RequestTestingTestDto testingTestDto) {
    validateTestSpecialization(testingTestDto);
    if (!testRepository.existsByIdAndStatus(testingTestDto.getTestId(), TestStatus.ACTIVE)) {
      throw new ValidationException(new ValidationError(ValidationErrorType.INVALID_STATUS, new HashMap<>()));
    }
    TestingTestEntity entity = testingTestMapper.toEntity(testingTestDto);
    testingTestRepository.save(entity);
    return new IdDto(entity.getId());
  }

  @Override
  public void delete(Long id) {
    testingTestRepository.deleteById(id);
  }

  private void validateTestSpecialization(RequestTestingTestDto testingTestDto) {
    boolean hasValidSpecialization = testingTestRepository.hasValidSpecialization(
        testingTestDto.getTestingId(),
        testingTestDto.getTestId());
    if (!hasValidSpecialization) {
      throw new IllegalArgumentException("You cannot add this test to this testing");
    }
  }
}
