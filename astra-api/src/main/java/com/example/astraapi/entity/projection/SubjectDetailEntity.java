package com.example.astraapi.entity.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDetailEntity {
  private Long id;
  private String title;
  private List<StepSpecializationProjection> specializations;
}
