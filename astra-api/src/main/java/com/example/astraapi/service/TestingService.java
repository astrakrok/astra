package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestingDto;
import com.example.astraapi.dto.TestingInfoDto;
import com.example.astraapi.dto.TestingShortTestDto;
import com.example.astraapi.dto.TestingTestQuestionDto;
import com.example.astraapi.dto.TestingWithSpecializationDto;

import java.util.List;
import java.util.Optional;

public interface TestingService {
  IdDto save(RequestTestingDto testingDto);

  List<TestingWithSpecializationDto> getWithSpecializations(Long examId);

  Optional<TestingInfoDto> getTestingInfo(Long id);

  List<TestingTestQuestionDto> getTestsQuestions(Long id);

  List<TestingShortTestDto> getNotSelectedTestingTests(Long id);
}
