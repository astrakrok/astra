package com.example.astraapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationStatisticInfoEntity {
  private Long lastCorrectCount;
  private Long lastTotalCount;
  private Long bestCorrectCount;
  private Long bestTotalCount;
  private TestingInfoEntity testing;
}
