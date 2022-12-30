package com.example.astraapi.dto.examination;

import com.example.astraapi.dto.testing.TestingInfoDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationStatisticDto {
  private Long lastCorrectCount;
  private Long lastTotalCount;
  private Long bestCorrectCount;
  private Long bestTotalCount;
  @JsonProperty("isSuccess")
  private boolean success;
  private TestingInfoDto testing;
}
