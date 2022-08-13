package com.example.astraapi.service.impl;

import com.example.astraapi.dto.ExaminationSearchDto;
import com.example.astraapi.dto.ExaminationTestDto;
import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestDto;
import com.example.astraapi.dto.TestFullDetailDto;
import com.example.astraapi.dto.TestShortDetailDto;
import com.example.astraapi.dto.TestVariantDto;
import com.example.astraapi.dto.TestingShortTestDto;
import com.example.astraapi.dto.TrainingSearchDto;
import com.example.astraapi.dto.TrainingTestDto;
import com.example.astraapi.entity.TestEntity;
import com.example.astraapi.mapper.TestMapper;
import com.example.astraapi.repository.TestRepository;
import com.example.astraapi.service.TestExamService;
import com.example.astraapi.service.TestService;
import com.example.astraapi.service.TestSubjectService;
import com.example.astraapi.service.TestVariantService;
import com.example.astraapi.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
  private final TestMapper testMapper;
  private final TestRepository testRepository;
  private final Validator<Collection<TestVariantDto>> testVariantsValidator;
  private final TestVariantService testVariantService;
  private final TestExamService testExamService;
  private final TestSubjectService testSubjectService;

  @Override
  @Transactional
  public IdDto save(RequestTestDto testDto) {
    List<TestVariantDto> testVariants = testDto.getVariants();
    testVariantsValidator.validate(testVariants);
    TestEntity entity = testMapper.toEntity(testDto);
    testRepository.save(entity);
    Long testId = entity.getId();
    testVariantService.save(testId, testVariants);
    testExamService.save(testId, testDto.getExamIds());
    testSubjectService.save(testId, testDto.getSubjectIds());
    return new IdDto(entity.getId());
  }

  @Override
  public List<TestShortDetailDto> getAll() {
    return testRepository.getAll().stream()
        .map(testMapper::toShortDetailDto)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<TestFullDetailDto> getDetailedTest(Long id) {
    return testRepository.getDetailedTestById(id)
        .map(testMapper::toFullDetailDto);
  }

  @Override
  public List<TrainingTestDto> getTrainingTests(TrainingSearchDto searchDto) {
    return testRepository.getTestingBySpecializationIdAndExamId(
            searchDto.getSpecializationId(),
            searchDto.getExamId(),
            searchDto.getCount()).stream()
        .map(testMapper::toTrainingDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<ExaminationTestDto> getExaminationTests(long count, ExaminationSearchDto searchDto) {
    return testRepository.getTestingBySpecializationIdAndExamId(
            searchDto.getSpecializationId(),
            searchDto.getExamId(),
            count).stream()
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
}
