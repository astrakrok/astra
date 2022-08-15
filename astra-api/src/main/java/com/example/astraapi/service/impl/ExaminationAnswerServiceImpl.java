package com.example.astraapi.service.impl;

import com.example.astraapi.dto.AnsweredTestDto;
import com.example.astraapi.dto.ExaminationAnswerDto;
import com.example.astraapi.dto.ExaminationResultDto;
import com.example.astraapi.dto.ExaminationSearchDto;
import com.example.astraapi.dto.ExaminationTestDto;
import com.example.astraapi.dto.TestVariantDto;
import com.example.astraapi.entity.ExaminationAnswerEntity;
import com.example.astraapi.entity.PropertyEntity;
import com.example.astraapi.mapper.ExaminationAnswerMapper;
import com.example.astraapi.meta.ConfigProperty;
import com.example.astraapi.repository.ExaminationAnswerRepository;
import com.example.astraapi.service.ExaminationAnswerService;
import com.example.astraapi.service.PropertyService;
import com.example.astraapi.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExaminationAnswerServiceImpl implements ExaminationAnswerService {
  private final TestService testService;
  private final PropertyService propertyService;
  private final ExaminationAnswerMapper examinationAnswerMapper;
  private final ExaminationAnswerRepository examinationAnswerRepository;

  @Override
  @Transactional
  public List<ExaminationTestDto> createTestsForExamination(
      Long examinationId,
      ExaminationSearchDto searchDto,
      Integer count
  ) {
    List<ExaminationTestDto> examinationTests = testService.getExaminationTests(count, searchDto);
    if (examinationTests.isEmpty()) {
      return Collections.emptyList();
    }
    List<ExaminationAnswerEntity> entities = examinationTests.stream()
        .map(ExaminationTestDto::getId)
        .map(testId -> examinationAnswerMapper.toEntity(examinationId, testId))
        .collect(Collectors.toList());
    examinationAnswerRepository.saveAll(entities);
    return examinationTests;
  }

  @Override
  public List<ExaminationTestDto> getExaminationTests(List<ExaminationAnswerDto> answers) {
    Map<Long, Long> testIdToUSerAnswer = getTestIdToUserAnswerMap(answers);
    List<Long> testsIds = getTestsIds(answers);
    List<ExaminationTestDto> examinationTests = testService.getExaminationTests(testsIds);
    examinationTests.forEach(test -> setUserAnswer(test, testIdToUSerAnswer));
    return examinationTests;
  }

  @Override
  public void updateAnswer(ExaminationAnswerDto examinationAnswerDto) {
    ExaminationAnswerEntity examinationAnswerEntity = examinationAnswerMapper.toEntity(examinationAnswerDto);
    examinationAnswerRepository.update(examinationAnswerEntity);
  }

  @Override
  public ExaminationResultDto getResult(Long examinationId) {
    List<ExaminationAnswerEntity> answerEntities = examinationAnswerRepository.getDetailedAnswersByExaminationId(examinationId);
    List<AnsweredTestDto> answeredTests = getAnsweredTests(answerEntities);
    PropertyEntity property = propertyService.getProperty(ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName())
        .orElseThrow(() -> new IllegalArgumentException("No such property"));
    Long correctCount = getCorrectCount(answeredTests);
    Long total = (long) answeredTests.size();
    Long correctness = getCorrectness(correctCount, total);
    Long threshold = getThresholdValue(property);
    return new ExaminationResultDto(
        answeredTests,
        correctCount,
        total,
        correctness >= threshold
    );
  }

  private Long getThresholdValue(PropertyEntity threshold) {
    return Long.parseLong(threshold.getValue());
  }

  private Long getCorrectness(Long correctCount, Long total) {
    if (total == null || total == 0) {
      return 0L;
    }
    return Math.round(correctCount * 100.0 / total);
  }

  private List<Long> getTestsIds(List<ExaminationAnswerDto> answers) {
    return answers.stream()
        .map(ExaminationAnswerDto::getTestId)
        .collect(Collectors.toList());
  }

  private Map<Long, Long> getTestIdToUserAnswerMap(List<ExaminationAnswerDto> answers) {
    Map<Long, Long> testIdToUserAnswer = new HashMap<>();
    for (ExaminationAnswerDto answer : answers) {
      testIdToUserAnswer.put(
          answer.getTestId(),
          answer.getVariantId()
      );
    }
    return testIdToUserAnswer;
  }

  private void setUserAnswer(ExaminationTestDto test, Map<Long, Long> testIdToUSerAnswer) {
    Long testId = test.getId();
    Long userAnswer = testIdToUSerAnswer.get(testId);
    test.setUserAnswer(userAnswer);
  }

  private List<AnsweredTestDto> getAnsweredTests(List<ExaminationAnswerEntity> entities) {
    return entities.stream()
        .map(entity -> examinationAnswerMapper.toAnsweredTestDto(entity.getTest(), entity.getVariantId()))
        .collect(Collectors.toList());
  }

  private Long getCorrectCount(List<AnsweredTestDto> answers) {
    return answers.stream()
        .filter(this::isCorrectUserAnswer)
        .count();
  }

  private boolean isCorrectUserAnswer(AnsweredTestDto answer) {
    return Objects.equals(
        answer.getUserAnswer(),
        getCorrectTestVariantId(answer.getVariants()));
  }

  private Long getCorrectTestVariantId(List<TestVariantDto> variants) {
    return variants.stream()
        .filter(TestVariantDto::isCorrect)
        .findFirst()
        .map(TestVariantDto::getId)
        .orElse(null);
  }
}
