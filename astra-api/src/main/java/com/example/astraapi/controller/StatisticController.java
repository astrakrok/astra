package com.example.astraapi.controller;

import com.example.astraapi.dto.examination.ExaminationStatisticDto;
import com.example.astraapi.dto.filter.StepsStatisticFilterDto;
import com.example.astraapi.dto.statistic.StepStatisticDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.STATISTIC)
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping("/examinations")
    public ResponseEntity<List<ExaminationStatisticDto>> getStatistics() {
        List<ExaminationStatisticDto> statistics = statisticService.getStatistics();
        return ResponseEntity.ok(statistics);
    }

    @PostMapping("/steps")
    public ResponseEntity<List<StepStatisticDto>> getStepsStatistic(
            @RequestBody StepsStatisticFilterDto filter
    ) {
        List<StepStatisticDto> statistics = statisticService.getStepsStatistic(filter);
        return ResponseEntity.ok(statistics);
    }
}
