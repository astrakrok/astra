package com.example.astraapi.dto.step;

import com.example.astraapi.annotation.TrimmedLength;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StepDto {
  private Long id;
  @NotNull
  @TrimmedLength(min = 3)
  private String title;
}
