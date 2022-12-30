package com.example.astraapi.dto.subject;

import com.example.astraapi.dto.specialization.StepSpecializationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSubjectDto {
  private Long id;
  private String title;
  private StepSpecializationDto specialization;
}
