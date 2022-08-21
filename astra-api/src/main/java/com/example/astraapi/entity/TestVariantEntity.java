package com.example.astraapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestVariantEntity {
  private Long id;
  private Long testId;
  private String title;
  private String titleSvg;
  private String explanation;
  private String explanationSvg;
  private boolean correct;
}
