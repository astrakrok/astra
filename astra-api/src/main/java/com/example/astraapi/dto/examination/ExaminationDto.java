package com.example.astraapi.dto.examination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationDto {
  private Long id;
  private Long userId;
  private Long testingId;
  private List<ExaminationAnswerDto> answers = new ArrayList<>();
}
