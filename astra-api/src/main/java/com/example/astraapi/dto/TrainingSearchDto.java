package com.example.astraapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSearchDto {
  @NotNull
  @Positive
  private long specializationId;
  @NotNull
  @Positive
  private long examId;
  @NotNull
  @Positive
  private long count;
}
