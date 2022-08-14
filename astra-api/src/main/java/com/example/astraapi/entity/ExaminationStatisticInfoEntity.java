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
  private Long id;
  private Long lastPercentage;
  private Long bestPercentage;
  private TestingInfoEntity testing;
}
