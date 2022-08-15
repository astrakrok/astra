package com.example.astraapi.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConfigProperty {
  EXAMINATION_THRESHOLD_PERCENTAGE("examinationThresholdPercentage");

  private final String name;
}
