package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.testing.RequestTestingDto;
import com.example.astraapi.dto.testing.TestingDescriptionDto;
import com.example.astraapi.dto.testing.TestingDto;
import com.example.astraapi.dto.testing.TestingInfoDto;
import com.example.astraapi.dto.test.TestingShortTestDto;
import com.example.astraapi.dto.test.TestingTestQuestionDto;
import com.example.astraapi.dto.testing.TestingWithSpecializationDto;
import com.example.astraapi.entity.TestingEntity;
import com.example.astraapi.mapper.TestingMapper;
import com.example.astraapi.meta.ConfigProperty;
import com.example.astraapi.repository.TestingRepository;
import com.example.astraapi.service.PropertyService;
import com.example.astraapi.service.TestService;
import com.example.astraapi.service.TestingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {
  private final TestService testService;
  private final TestingMapper testingMapper;
  private final PropertyService propertyService;
  private final TestingRepository testingRepository;

  @Override
  public IdDto save(RequestTestingDto testingDto) {
    TestingEntity entity = testingMapper.toEntity(testingDto);
    testingRepository.save(entity);
    return new IdDto(entity.getId());
  }

  @Override
  public List<TestingWithSpecializationDto> getWithSpecializations(Long examId) {
    return testingRepository.getByExamIdWithSpecialization(examId).stream()
        .map(testingMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<TestingInfoDto> getTestingInfo(Long id) {
    return testingRepository.findTestingInfoById(id)
        .map(testingMapper::toInfoDto);
  }

  @Override
  public List<TestingTestQuestionDto> getTestsQuestions(Long id) {
    return testingRepository.getTestingTestsByTestingId(id).stream()
        .map(testingMapper::toTestQuestionDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<TestingShortTestDto> getNotSelectedTestingTests(Long id) {
    return testService.getNotYetSelectedTestingTests(id);
  }

  @Override
  public TestingDescriptionDto getDescription() {
    Map<String, String> properties = propertyService.getProperties(Set.of(
        ConfigProperty.TRAINING_DESCRIPTION.getName(),
        ConfigProperty.EXAMINATION_DESCRIPTION.getName()));
    return new TestingDescriptionDto(
        properties.get(ConfigProperty.TRAINING_DESCRIPTION.getName()),
        properties.get(ConfigProperty.EXAMINATION_DESCRIPTION.getName())
    );
  }

  @Override
  public List<TestingDto> getAvailableTestings() {
    return testingRepository.getAvailable().stream()
        .map(testingMapper::toDto)
        .collect(Collectors.toList());
  }
}
