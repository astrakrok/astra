package com.example.astraapi.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectStatisticDto {
  private Long id;
  private String title;
  private StatisticDto statistic;
}
