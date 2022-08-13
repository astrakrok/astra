package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestingTestDto;
import com.example.astraapi.entity.TestingTestEntity;
import com.example.astraapi.mapper.TestingTestMapper;
import com.example.astraapi.repository.TestingTestRepository;
import com.example.astraapi.service.TestingTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestingTestServiceImpl implements TestingTestService {
  private final TestingTestRepository testingTestRepository;
  private final TestingTestMapper testingTestMapper;

  @Override
  @Transactional
  public IdDto save(RequestTestingTestDto testingTestDto) {
    validateTestSpecialization(testingTestDto);
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
        testingTestDto.getTestId(),
        testingTestDto.getTestingId());
    if (!hasValidSpecialization) {
      throw new IllegalArgumentException("You cannot add this test to this testing");
    }
  }
}
