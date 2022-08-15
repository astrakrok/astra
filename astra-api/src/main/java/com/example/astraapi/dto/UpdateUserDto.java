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
public class UpdateUserDto {
  @TrimmedLength(min = 2)
  private String name;
  @TrimmedLength(min = 2)
  private String surname;
  private String course;
  private String school;
  private Long specializationId;
}
