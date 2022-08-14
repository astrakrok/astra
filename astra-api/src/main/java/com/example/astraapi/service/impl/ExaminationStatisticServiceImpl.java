package com.example.astraapi.service.impl;

import com.example.astraapi.dto.ExaminationDto;
import com.example.astraapi.dto.ExaminationStatisticDto;
import com.example.astraapi.entity.ExaminationStatisticEntity;
import com.example.astraapi.entity.projection.ExaminationStatisticProjection;
import com.example.astraapi.mapper.ExaminationStatisticMapper;
import com.example.astraapi.repository.ExaminationStatisticRepository;
import com.example.astraapi.service.ExaminationStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExaminationStatisticServiceImpl implements ExaminationStatisticService {
  private final ExaminationStatisticMapper examinationStatisticMapper;
  private final ExaminationStatisticRepository examinationStatisticRepository;

  @Override
  @Transactional
  public void aggregate(ExaminationDto examination) {
    ExaminationStatisticProjection statistic = examinationStatisticRepository.calculateStatistic(examination.getId())
        .orElseThrow(() -> new IllegalArgumentException("No such examination"));
    Long userId = examination.getUserId();
    Long testingId = examination.getTestingId();
    ExaminationStatisticEntity examinationStatisticEntity = examinationStatisticRepository.findByUserIdAndTestingId(userId, testingId)
        .orElse(getDefaultStatisticItem(userId, testingId));
    merge(examinationStatisticEntity, statistic);
    if (examinationStatisticEntity.getId() == null) {
      examinationStatisticRepository.save(examinationStatisticEntity);
    } else {
      examinationStatisticRepository.updatePercentage(examinationStatisticEntity);
    }
  }

  @Override
  public List<ExaminationStatisticDto> getStatistics(Long userId) {
    return examinationStatisticRepository.getAllWithTestingByUserId(userId).stream()
        .map(examinationStatisticMapper::toDto)
        .collect(Collectors.toList());
  }

  private ExaminationStatisticEntity getDefaultStatisticItem(Long userId, Long testingId) {
    return new ExaminationStatisticEntity(
        null,
        userId,
        testingId,
        null,
        null
    );
  }

  private void merge(ExaminationStatisticEntity statisticEntity, ExaminationStatisticProjection statisticProjection) {
    Long correctPercentage = calculateCorrectPercentage(
        statisticProjection.getCorrectCount(),
        statisticProjection.getTotalCount());
    Long bestPercentage = getBestPercentage(correctPercentage, statisticEntity.getBestPercentage());
    statisticEntity.setLastPercentage(correctPercentage);
    statisticEntity.setBestPercentage(bestPercentage);
  }

  private Long calculateCorrectPercentage(Long correct, Long total) {
    if (total == null || total == 0) {
      return 0L;
    }
    return Math.round(correct * 100.0 / total);
  }

  private Long getBestPercentage(Long current, Long last) {
    if (last == null) {
      return current;
    }
    return Math.max(current, last);
  }
}
