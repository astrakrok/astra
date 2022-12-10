package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.test.TestingShortTestDto;
import com.example.astraapi.dto.test.TestingTestQuestionDto;
import com.example.astraapi.dto.testing.RequestTestingDto;
import com.example.astraapi.dto.testing.TestingDescriptionDto;
import com.example.astraapi.dto.testing.TestingDetailDto;
import com.example.astraapi.dto.testing.TestingDto;
import com.example.astraapi.dto.testing.TestingInfoDto;
import com.example.astraapi.dto.testing.TestingWithSpecializationDto;

import java.util.List;
import java.util.Optional;

public interface TestingService {
  IdDto save(RequestTestingDto testingDto);

  List<TestingWithSpecializationDto> getWithSpecializations(Long examId);

  Optional<TestingInfoDto> getTestingInfo(Long id);

  List<TestingTestQuestionDto> getTestsQuestions(Long id);

  List<TestingShortTestDto> getNotSelectedTestingTests(Long id);

  TestingDescriptionDto getDescription();

  List<TestingDetailDto> getAvailableTestings();

  TestingDto getOne(Long examId, Long specializationId);
}
