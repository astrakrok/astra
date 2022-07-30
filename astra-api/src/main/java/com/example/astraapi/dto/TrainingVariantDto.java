package com.example.astraapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingVariantDto {
  private Long id;
  private Long testId;
  private String title;
  private String explanation;
  private boolean correct;
}
