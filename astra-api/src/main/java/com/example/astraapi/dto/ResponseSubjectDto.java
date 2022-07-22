package com.example.astraapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSubjectDto {
  private Long subjectId;
  private String subjectTitle;
  private List<SpecializationDto> specializations;
}
