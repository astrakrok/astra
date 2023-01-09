package com.example.astraapi.service.impl;

import com.example.astraapi.dto.TrainingSearchDto;
import com.example.astraapi.dto.examination.ExaminationSearchDto;
import com.example.astraapi.dto.filter.AdminTestFilterDto;
import com.example.astraapi.dto.test.*;
import com.example.astraapi.dto.test.variant.TestVariantDto;
import com.example.astraapi.entity.TestEntity;
import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.mapper.TestMapper;
import com.example.astraapi.meta.TestStatus;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.repository.TestRepository;
import com.example.astraapi.service.TestService;
import com.example.astraapi.service.TestSubjectService;
import com.example.astraapi.service.TestVariantService;
import com.example.astraapi.util.PageUtils;
import com.example.astraapi.validation.ErrorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestMapper testMapper;
    private final TestRepository testRepository;
    private final ErrorValidator<RequestTestDto> testValidator;
    private final TestVariantService testVariantService;
    private final TestSubjectService testSubjectService;

    @Override
    @Transactional
    public TestFullDetailDto save(RequestTestDto testDto) {
        List<ValidationError> errors = testValidator.validate(testDto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Long id = saveWithStatus(testDto, TestStatus.ACTIVE);
        return getDetailedTest(id).orElse(null);
    }

    @Override
    public TestFullDetailDto saveDraft(RequestTestDto testDto) {
        Long id = saveWithStatus(testDto, TestStatus.DRAFT);
        return getDetailedTest(id).orElse(null);
    }

    @Override
    @Transactional
    public Optional<TestFullDetailDto> update(Long id, RequestTestDto testDto) {
        List<ValidationError> errors = testValidator.validate(testDto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        updateWithStatus(id, testDto, TestStatus.ACTIVE);
        return getDetailedTest(id);
    }

    @Override
    public Optional<TestFullDetailDto> updateDraft(Long id, RequestTestDto testDto) {
        if (!testRepository.existsByIdAndStatus(id, TestStatus.DRAFT)) {
            throw new ValidationException(new ValidationError(ValidationErrorType.INVALID_STATUS, new HashMap<>()));
        }
        updateWithStatus(id, testDto, TestStatus.DRAFT);
        return getDetailedTest(id);
    }

    @Override
    public Page<TestShortDetailDto> getAll(AdminTestFilterDto filter, Pageable pageable) {
        return PageUtils.mapPage(
                testRepository.getAll(
                        filter.getSearchText(),
                        filter.getStatus(),
                        filter.getImportId(),
                        pageable),
                testMapper::toShortDetailDto);
    }

    @Override
    public Optional<TestFullDetailDto> getDetailedTest(Long id) {
        return testRepository.getDetailedTestById(id)
                .map(testMapper::toFullDetailDto);
    }

    @Override
    public List<TrainingTestDto> getTrainingTests(TrainingSearchDto searchDto) {
        return testRepository.getTestsByTestingId(
                        searchDto.getTestingId(),
                        searchDto.getCount()).stream()
                .map(testMapper::toTrainingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExaminationTestDto> getExaminationTests(long count, ExaminationSearchDto searchDto) {
        return testRepository.getTestsByTestingId(searchDto.getTestingId(), count).stream()
                .map(testMapper::toExaminationDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExaminationTestDto> getExaminationTests(List<Long> ids) {
        return testRepository.getTestsByIds(ids).stream()
                .map(testMapper::toExaminationDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestingShortTestDto> getNotYetSelectedTestingTests(Long testingId) {
        return testRepository.getNotRelatedTestingTests(testingId).stream()
                .map(testMapper::toShortTestDto)
                .collect(Collectors.toList());
    }

    private Long saveWithStatus(RequestTestDto testDto, TestStatus status) {
        List<TestVariantDto> testVariants = testDto.getVariants();
        TestEntity entity = testMapper.toEntity(testDto, status);
        testRepository.save(entity);
        Long testId = entity.getId();
        testVariantService.save(testId, testVariants);
        testSubjectService.save(testId, testDto.getSubjectIds());
        return entity.getId();
    }

    private void updateWithStatus(Long id, RequestTestDto testDto, TestStatus status) {
        TestEntity testEntity = testMapper.toEntity(id, testDto, status);
        testRepository.update(testEntity);
        testVariantService.update(id, testDto.getVariants());
        testSubjectService.update(id, testDto.getSubjectIds());
    }
}
