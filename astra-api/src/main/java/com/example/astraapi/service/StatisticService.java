package com.example.astraapi.service;

import com.example.astraapi.dto.examination.ExaminationStatisticDto;
import com.example.astraapi.dto.statistic.StepStatisticDto;

import java.util.List;

public interface StatisticService {
  List<ExaminationStatisticDto> getStatistics();

  List<StepStatisticDto> getStepsStatistic();
}
