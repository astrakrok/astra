package com.example.astraapi.service;

import com.example.astraapi.dto.examination.ExaminationStatisticDto;

import java.util.List;

public interface ExaminationStatisticService {
  List<ExaminationStatisticDto> getStatistics(Long userId);
}
