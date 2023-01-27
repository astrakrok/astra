package com.example.astraapi.service.impl;

import com.example.astraapi.dto.examination.ExaminationStatisticDto;
import com.example.astraapi.dto.filter.StepsStatisticFilterDto;
import com.example.astraapi.dto.statistic.StepStatisticDto;
import com.example.astraapi.mapper.ExaminationStatisticMapper;
import com.example.astraapi.repository.ExaminationStatisticRepository;
import com.example.astraapi.service.AuthContext;
import com.example.astraapi.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final ExaminationStatisticMapper examinationStatisticMapper;
    private final ExaminationStatisticRepository examinationStatisticRepository;
    private final AuthContext authContext;

    @Override
    public List<ExaminationStatisticDto> getStatistics() {
        Long userId = authContext.getUser().getId();
        return examinationStatisticRepository.getAllWithTestingByUserId(userId).stream()
                .map(examinationStatisticMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StepStatisticDto> getStepsStatistic(StepsStatisticFilterDto filter) {
        Long userId = authContext.getUser().getId();
        return examinationStatisticRepository.getStepsStatisticByUserId(
                        userId,
                        filter.getSpecializationId()
                ).stream()
                .map(examinationStatisticMapper::toDto)
                .collect(Collectors.toList());
    }
}
