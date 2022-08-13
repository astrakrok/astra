package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestingDto;
import com.example.astraapi.dto.TestingInfoDto;
import com.example.astraapi.dto.TestingTestQuestionDto;
import com.example.astraapi.dto.TestingWithSpecializationDto;
import com.example.astraapi.entity.TestingEntity;
import com.example.astraapi.mapper.TestingMapper;
import com.example.astraapi.repository.TestingRepository;
import com.example.astraapi.service.TestingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {
  private final TestingMapper testingMapper;
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
}
