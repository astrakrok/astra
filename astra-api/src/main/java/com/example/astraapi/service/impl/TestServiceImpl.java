package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.TrainingSearchDto;
import com.example.astraapi.dto.examination.ExaminationSearchDto;
import com.example.astraapi.dto.test.ExaminationTestDto;
import com.example.astraapi.dto.test.RequestTestDto;
import com.example.astraapi.dto.test.TestFullDetailDto;
import com.example.astraapi.dto.test.TestShortDetailDto;
import com.example.astraapi.dto.test.TestingShortTestDto;
import com.example.astraapi.dto.test.TrainingTestDto;
import com.example.astraapi.dto.test.variant.TestVariantDto;
import com.example.astraapi.entity.TestEntity;
import com.example.astraapi.mapper.TestMapper;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.repository.TestRepository;
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
    testSubjectService.save(testId, testDto.getSubjectIds());
    return new IdDto(entity.getId());
  }

  @Override
  @Transactional
  public void update(Long id, RequestTestDto testDto) {
    testVariantsValidator.validate(testDto.getVariants());
    TestEntity testEntity = testMapper.toEntity(id, testDto);
    testRepository.update(testEntity);
    testVariantService.update(id, testDto.getVariants());
    testSubjectService.update(id, testDto.getSubjectIds());
  }

  @Override
  public Page<TestShortDetailDto> getAll(Pageable pageable) {
    Page<TestShortDetailDto> page = testMapper.toShortDetailDto(testRepository.getAll(pageable));
    if (page == null) {
      return new Page<>();
    }
    page.setPageSize(pageable.getPageSize());
    return page;
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
}
