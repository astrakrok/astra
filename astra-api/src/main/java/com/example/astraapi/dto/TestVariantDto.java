package com.example.astraapi.dto;

import com.example.astraapi.annotation.TrimmedLength;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestVariantDto {
  private Long id;
  private Long testId;
  @NotNull
  @TrimmedLength(min = 1)
  private String title;
  private String titleSvg;
  @NotNull
  @TrimmedLength(min = 10)
  private String explanation;
  private String explanationSvg;
  @JsonProperty("isCorrect")
  private boolean correct;
}
