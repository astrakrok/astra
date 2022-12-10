package com.example.astraapi.service;

import com.example.astraapi.dto.examination.ExaminationDto;
import com.example.astraapi.dto.examination.ExaminationStatisticDto;

import java.util.List;

public interface ExaminationStatisticService {
  void aggregate(ExaminationDto examination);

  List<ExaminationStatisticDto> getStatistics(Long userId);
}
