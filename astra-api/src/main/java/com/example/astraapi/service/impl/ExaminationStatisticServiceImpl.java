package com.example.astraapi.service.impl;

import com.example.astraapi.dto.examination.ExaminationStatisticDto;
import com.example.astraapi.mapper.ExaminationStatisticMapper;
import com.example.astraapi.repository.ExaminationStatisticRepository;
import com.example.astraapi.service.ExaminationStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExaminationStatisticServiceImpl implements ExaminationStatisticService {
  private final ExaminationStatisticMapper examinationStatisticMapper;
  private final ExaminationStatisticRepository examinationStatisticRepository;

  @Override
  public List<ExaminationStatisticDto> getStatistics(Long userId) {
    // TODO don't use examinations_statistics table, use data from examinations and examinations_answers instead
    return examinationStatisticRepository.getAllWithTestingByUserId(userId).stream()
        .map(examinationStatisticMapper::toDto)
        .collect(Collectors.toList());
  }
}
