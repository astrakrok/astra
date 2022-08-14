package com.example.astraapi.service;

import com.example.astraapi.dto.ExaminationDto;
import com.example.astraapi.dto.ExaminationStatisticDto;

import java.util.List;

public interface ExaminationStatisticService {
  void aggregate(ExaminationDto examination);

  List<ExaminationStatisticDto> getStatistics(Long userId);
}
