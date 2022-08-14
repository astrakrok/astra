package com.example.astraapi.entity.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationStatisticProjection {
  private Long correctCount;
  private Long totalCount;
}
