package com.example.astraapi.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecializationStatisticDto {
  private Long id;
  private String title;
  private List<SubjectStatisticDto> subjects = new ArrayList<>();
}
