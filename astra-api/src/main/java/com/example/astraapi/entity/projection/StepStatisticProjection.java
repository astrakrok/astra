package com.example.astraapi.entity.projection;

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
public class StepStatisticProjection {
  private Long id;
  private String title;
  private List<SpecializationStatisticProjection> specializations = new ArrayList<>();
}
