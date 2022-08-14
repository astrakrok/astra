package com.example.astraapi.dto;

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
  private Long id;
  private Long last;
  private Long best;
  @JsonProperty("isSuccess")
  private boolean success;
  private TestingInfoDto testing;
}
