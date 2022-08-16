package com.example.astraapi.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConfigProperty {
  EXAMINATION_THRESHOLD_PERCENTAGE("examinationThresholdPercentage"),
  TRAINING_DESCRIPTION("trainingDescription"),
  EXAMINATION_DESCRIPTION("examinationDescription");

  private final String name;
}
