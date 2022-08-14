package com.example.astraapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationStatisticEntity {
  private Long id;
  private Long userId;
  private Long testingId;
  private Long lastPercentage;
  private Long bestPercentage;
}
