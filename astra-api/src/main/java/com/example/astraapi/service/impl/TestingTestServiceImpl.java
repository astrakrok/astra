package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.test.RequestTestingDetailTestDto;
import com.example.astraapi.dto.test.RequestTestingTestDto;
import com.example.astraapi.dto.testing.TestingDetailDto;
import com.example.astraapi.entity.TestingEntity;
import com.example.astraapi.entity.TestingTestEntity;
import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.mapper.TestingMapper;
import com.example.astraapi.mapper.TestingTestMapper;
import com.example.astraapi.meta.TestStatus;
import com.example.astraapi.meta.TestingStatus;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.repository.TestRepository;
import com.example.astraapi.repository.TestingRepository;
import com.example.astraapi.repository.TestingTestRepository;
import com.example.astraapi.service.TestingTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestingTestServiceImpl implements TestingTestService {
    private final TestingTestRepository testingTestRepository;
    private final TestingTestMapper testingTestMapper;
    private final TestingMapper testingMapper;
    private final TestRepository testRepository;
    private final TestingRepository testingRepository;

    @Override
    @Transactional
    public IdDto save(RequestTestingTestDto testingTestDto) {
        validateTestSpecialization(testingTestDto);
        if (!testRepository.existsByIdAndStatus(testingTestDto.getTestId(), TestStatus.ACTIVE)) {
            throw new ValidationException(new ValidationError(ValidationErrorType.INVALID_STATUS, Map.of(
                    "item", "test",
                    "shouldBe", TestStatus.ACTIVE)));
        }
        if (!testingRepository.existsByIdAndStatus(testingTestDto.getTestingId(), TestingStatus.DRAFT)) {
            throw new ValidationException(new ValidationError(ValidationErrorType.INVALID_STATUS, Map.of(
                    "item", "testing",
                    "shouldBe", TestStatus.DRAFT)));
        }
        if (testingTestRepository.existsByTestingIdAndTestId(testingTestDto.getTestingId(), testingTestDto.getTestId())) {
            throw new ValidationException(new ValidationError(ValidationErrorType.CONFLICT, Map.of()));
        }
        TestingTestEntity entity = testingTestMapper.toEntity(testingTestDto);
        testingTestRepository.save(entity);
        return new IdDto(entity.getId());
    }

    @Override
    @Transactional
    public IdDto save(RequestTestingDetailTestDto testingDetailTestDto) {
        TestingEntity testingEntity = this.testingRepository.getByExamIdAndSpecializationId(testingDetailTestDto.getExamId(), testingDetailTestDto.getSpecializationId());
        if (testingEntity == null) {
            testingEntity = testingMapper.toEntity(testingDetailTestDto, TestingStatus.DRAFT);
            testingRepository.save(testingEntity);
        }
        return save(new RequestTestingTestDto(
                testingEntity.getId(),
                testingDetailTestDto.getTestId()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!testingTestRepository.hasTestingStatus(id, TestingStatus.DRAFT)) {
            throw new ValidationException(new ValidationError(ValidationErrorType.INVALID_STATUS, Map.of("item", "testing")));
        }
        testingTestRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(RequestTestingTestDto testingTest) {
        if (!testingRepository.existsByIdAndStatus(testingTest.getTestingId(), TestingStatus.DRAFT)) {
            throw new ValidationException(new ValidationError(ValidationErrorType.INVALID_STATUS, Map.of("item", "testing")));
        }
        testingTestRepository.deleteByTestingIdAndTestId(
                testingTest.getTestingId(),
                testingTest.getTestId());
    }

    @Override
    @Transactional
    public List<TestingDetailDto> getTestings(Long testId) {
        return testingTestRepository.getTestings(testId).stream()
                .map(testingMapper::toDetailDto)
                .collect(Collectors.toList());
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
