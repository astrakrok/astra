package com.example.astraapi.service.impl;

import com.example.astraapi.dto.ExaminationAnswerDto;
import com.example.astraapi.dto.ExaminationSearchDto;
import com.example.astraapi.dto.ExaminationTestDto;
import com.example.astraapi.entity.ExaminationAnswerEntity;
import com.example.astraapi.mapper.ExaminationAnswerMapper;
import com.example.astraapi.repository.ExaminationAnswerRepository;
import com.example.astraapi.service.ExaminationAnswerService;
import com.example.astraapi.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExaminationAnswerServiceImpl implements ExaminationAnswerService {
  private final TestService testService;
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

  private List<Long> getTestsIds(List<ExaminationAnswerDto> answers) {
    return answers.stream()
        .map(ExaminationAnswerDto::getTestId)
        .collect(Collectors.toList());
  }

  private Map<Long, Long> getTestIdToUserAnswerMap(List<ExaminationAnswerDto> answers) {
    Map<Long, Long> testIdToUserAnswer = new HashMap<>();
    for (ExaminationAnswerDto answer: answers) {
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
}
