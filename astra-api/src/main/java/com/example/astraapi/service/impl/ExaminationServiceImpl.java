package com.example.astraapi.service.impl;

import com.example.astraapi.config.ExaminationProperties;
import com.example.astraapi.dto.ExaminationAnswerDto;
import com.example.astraapi.dto.ExaminationSearchDto;
import com.example.astraapi.dto.ExaminationStateDto;
import com.example.astraapi.dto.ExaminationTestDto;
import com.example.astraapi.entity.ExaminationEntity;
import com.example.astraapi.mapper.ExaminationMapper;
import com.example.astraapi.repository.ExaminationRepository;
import com.example.astraapi.service.AuthContext;
import com.example.astraapi.service.ExaminationAnswerService;
import com.example.astraapi.service.ExaminationService;
import com.example.astraapi.service.TimeZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExaminationServiceImpl implements ExaminationService {
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
            searchDto.getSpecializationId(),
            searchDto.getExamId(),
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
  public void updateAnswer(Long examinationId, ExaminationAnswerDto examinationAnswerDto) {
    validateExamination(examinationId);
    examinationAnswerDto.setExaminationId(examinationId);
    examinationAnswerService.updateAnswer(examinationAnswerDto);
  }

  private ExaminationEntity createExamination(ExaminationSearchDto searchDto) {
    LocalDateTime finishedAt = LocalDateTime.now()
        .plusMinutes(examinationProperties.getDurationInMinutes())
        .plusSeconds(examinationProperties.getFinishedAtDeviationSeconds());
    LocalDateTime finishedAtUtc = timeZoneService.toUtc(finishedAt);
    ExaminationEntity examinationEntity = examinationMapper.toEntity(
        authContext.getUser().getId(),
        searchDto.getSpecializationId(),
        searchDto.getExamId(),
        finishedAtUtc);
    examinationRepository.save(examinationEntity);
    return examinationEntity;
  }

  private List<ExaminationTestDto> getExaminationTests(ExaminationEntity entity) {
    List<ExaminationAnswerDto> answers = examinationMapper.toDto(entity).getAnswers();
    return examinationAnswerService.getExaminationTests(answers);
  }

  private void validateExamination(Long examinationId) {
    LocalDateTime now = timeZoneService.toUtc(LocalDateTime.now());
    if (!examinationRepository.exists(examinationId, now)) {
      throw new IllegalArgumentException("You cannot modify answers for expired or non-existing examination");
    }
  }
}
