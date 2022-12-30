package com.example.astraapi.controller;

import com.example.astraapi.dto.examination.ExaminationStatisticDto;
import com.example.astraapi.dto.statistic.StepStatisticDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoint.STATISTIC)
@RequiredArgsConstructor
public class StatisticController {
  private final StatisticService statisticService;

  @GetMapping("/examinations")
  public List<ExaminationStatisticDto> getStatistics() {
    return statisticService.getStatistics();
  }

  @GetMapping("/steps")
  public List<StepStatisticDto> getStepsStatistic() {
    return statisticService.getStepsStatistic();
  }
}
