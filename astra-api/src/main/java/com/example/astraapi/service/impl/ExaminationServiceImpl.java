package com.example.astraapi.service.impl;

import com.example.astraapi.config.ExaminationProperties;
import com.example.astraapi.dto.ExaminationAnswerDto;
import com.example.astraapi.dto.ExaminationDto;
import com.example.astraapi.dto.ExaminationResultDto;
import com.example.astraapi.dto.ExaminationSearchDto;
import com.example.astraapi.dto.ExaminationStateDto;
import com.example.astraapi.dto.ExaminationStatisticDto;
import com.example.astraapi.dto.ExaminationTestDto;
import com.example.astraapi.entity.ExaminationEntity;
import com.example.astraapi.mapper.ExaminationMapper;
import com.example.astraapi.repository.ExaminationRepository;
import com.example.astraapi.service.AuthContext;
import com.example.astraapi.service.ExaminationAnswerService;
import com.example.astraapi.service.ExaminationService;
import com.example.astraapi.service.ExaminationStatisticService;
import com.example.astraapi.service.TimeZoneService;
import com.example.astraapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExaminationServiceImpl implements ExaminationService {
  private final ExaminationStatisticService examinationStatisticService;
  private final TransactionService transactionService;
  private final ExaminationProperties examinationProperties;
  private final ExaminationRepository examinationRepository;
  private final ExaminationMapper examinationMapper;
  private final ExaminationAnswerService examinationAnswerService;
  private final TimeZoneService timeZoneService;
  private final AuthContext authContext;

  @Override
  @Transactional
  public ExaminationStateDto start(ExaminationSearchDto searchDto) {
    ExaminationEntity examination = examinationRepository.findExaminationWithAnswers(
            authContext.getUser().getId(),
            searchDto.getTestingId(),
            timeZoneService.toUtc(LocalDateTime.now()))
        .orElseGet(() -> createExamination(searchDto));
    List<ExaminationTestDto> tests = examination.getAnswers().isEmpty() ? (
        examinationAnswerService.createTestsForExamination(
            examination.getId(),
            searchDto,
            examinationProperties.getCount())
    ) : (
        getExaminationTests(examination)
    );
    return new ExaminationStateDto(
        examination.getId(),
        tests,
        examination.getFinishedAt());
  }

  @Override
  public void updateAnswer(Long id, ExaminationAnswerDto examinationAnswerDto) {
    validateExamination(id);
    examinationAnswerDto.setExaminationId(id);
    examinationAnswerService.updateAnswer(examinationAnswerDto);
  }

  @Override
  @Transactional
  public ExaminationResultDto finish(Long id) {
    validateExamination(id);
    LocalDateTime finishedAt = timeZoneService.toUtc(LocalDateTime.now());
    examinationRepository.updateFinishedAtById(id, finishedAt);
    return examinationAnswerService.getResult(id);
  }

  @Override
  @Transactional
  public List<ExaminationStatisticDto> getStatistics() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime finishedAtBefore = timeZoneService.toUtc(now);
    Long userId = authContext.getUser().getId();
    List<ExaminationDto> examinations = examinationRepository.getAllByUserIdAndFinishedAtBeforeOrderById(
            userId,
            finishedAtBefore).stream()
        .map(examinationMapper::toDto)
        .collect(Collectors.toList());
    examinations.forEach(examination -> transactionService.execute(() -> processExamination(examination)));
    List<ExaminationStatisticDto> statistics = examinationStatisticService.getStatistics(userId);
    updateStatisticsSuccess(statistics);
    return statistics;
  }

  private ExaminationEntity createExamination(ExaminationSearchDto searchDto) {
    LocalDateTime finishedAt = LocalDateTime.now()
        .plusMinutes(examinationProperties.getDurationInMinutes())
        .plusSeconds(examinationProperties.getFinishedAtDeviationSeconds());
    LocalDateTime finishedAtUtc = timeZoneService.toUtc(finishedAt);
    ExaminationEntity examinationEntity = examinationMapper.toEntity(
        authContext.getUser().getId(),
        searchDto.getTestingId(),
        finishedAtUtc);
    examinationRepository.save(examinationEntity);
    return examinationEntity;
  }

  private List<ExaminationTestDto> getExaminationTests(ExaminationEntity entity) {
    List<ExaminationAnswerDto> answers = examinationMapper.toDto(entity).getAnswers();
    return examinationAnswerService.getExaminationTests(answers);
  }

  private void validateExamination(Long id) {
    Long userId = authContext.getUser().getId();
    LocalDateTime now = timeZoneService.toUtc(LocalDateTime.now());
    if (!examinationRepository.exists(id, userId, now)) {
      throw new IllegalArgumentException("You cannot modify answers for expired or non-existing examination");
    }
  }

  private void processExamination(ExaminationDto examination) {
    examinationStatisticService.aggregate(examination);
    examinationRepository.deleteById(examination.getId());
  }

  private void updateStatisticsSuccess(List<ExaminationStatisticDto> statistics) {
    statistics.stream()
        .filter(statistic -> statistic.getLast() >= examinationProperties.getThresholdPercentage())
        .forEach(statistic -> statistic.setSuccess(true));
  }
}
