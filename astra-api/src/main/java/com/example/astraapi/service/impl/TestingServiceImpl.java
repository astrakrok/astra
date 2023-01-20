package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminAvailableTestingTestsFilterDto;
import com.example.astraapi.dto.filter.AdminTestingTestsFilterDto;
import com.example.astraapi.dto.test.TestingShortTestDto;
import com.example.astraapi.dto.test.TestingTestQuestionDto;
import com.example.astraapi.dto.testing.*;
import com.example.astraapi.entity.TestingEntity;
import com.example.astraapi.entity.projection.TestingTestSimpleProjection;
import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.mapper.TestingMapper;
import com.example.astraapi.meta.ConfigProperty;
import com.example.astraapi.meta.TestingStatus;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.model.TestingPage;
import com.example.astraapi.model.validation.ValidationError;
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
        TestingEntity entity = testingMapper.toEntity(testingDto, TestingStatus.DRAFT);
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
    public TestingPage<TestingTestQuestionDto> getTestsQuestions(Long id, AdminTestingTestsFilterDto filter, Pageable pageable) {
        TestingPage<TestingTestSimpleProjection> page = testingRepository.getTestingTestsByTestingId(
                id,
                filter.getSearchText(),
                pageable);
        return page == null ? new TestingPage<>() : page.map(testingMapper::toTestQuestionDto);
    }

    @Override
    public Page<TestingShortTestDto> getNotSelectedTestingTests(Long id, AdminAvailableTestingTestsFilterDto filter, Pageable pageable) {
        return testService.getNotYetSelectedTestingTests(id, filter, pageable);
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
    public List<TestingDetailDto> getAvailableTestings() {
        return testingRepository.getAvailable().stream()
                .map(testingMapper::toDetailDto)
                .collect(Collectors.toList());
    }

    @Override
    public TestingDto getOne(Long examId, Long specializationId) {
        TestingEntity testing = testingRepository.getByExamIdAndSpecializationId(examId, specializationId);
        return testingMapper.toDto(testing);
    }

    @Override
    public Optional<TestingInfoDto> activate(Long id) {
        if (testingRepository.getTestsCount(id) == 0) {
            throw new ValidationException(new ValidationError(ValidationErrorType.EMPTY));
        }
        testingRepository.updateStatusById(id, TestingStatus.ACTIVE);
        return getTestingInfo(id);
    }
}
