package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminTestingTestsFilterDto;
import com.example.astraapi.dto.test.TestingShortTestDto;
import com.example.astraapi.dto.test.TestingTestQuestionDto;
import com.example.astraapi.dto.testing.*;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.model.TestingPage;

import java.util.List;
import java.util.Optional;

public interface TestingService {
    IdDto save(RequestTestingDto testingDto);

    List<TestingWithSpecializationDto> getWithSpecializations(Long examId);

    Optional<TestingInfoDto> getTestingInfo(Long id);

    TestingPage<TestingTestQuestionDto> getTestsQuestions(Long id, AdminTestingTestsFilterDto filter, Pageable pageable);

    List<TestingShortTestDto> getNotSelectedTestingTests(Long id);

    TestingDescriptionDto getDescription();

    List<TestingDetailDto> getAvailableTestings();

    TestingDto getOne(Long examId, Long specializationId);

    Optional<TestingInfoDto> activate(Long id);
}
