package com.example.astraapi.dto;

import com.example.astraapi.annotation.TrimmedLength;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestVariantDto {
  private Long id;
  private Long testId;
  @TrimmedLength(min = 1)
  private String title;
  @TrimmedLength(min = 10)
  private String explanation;
  private boolean correct;
}