package com.example.astraapi.dto;

import com.example.astraapi.meta.Regex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
  @Pattern(regexp = Regex.TEXT)
  private String name;
  @Pattern(regexp = Regex.TEXT)
  private String surname;
  @Min(1)
  @Max(6)
  private Integer course;
  private String school;
  private Long specializationId;
}
