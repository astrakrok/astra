package com.example.astraapi.entity.projection;

import com.example.astraapi.entity.StepEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StepSpecializationProjection {
  private Long id;
  private String title;
  private StepEntity step;
}
