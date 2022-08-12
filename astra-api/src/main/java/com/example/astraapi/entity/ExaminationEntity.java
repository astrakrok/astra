package com.example.astraapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationEntity {
  private Long id;
  private Long userId;
  private Long specializationId;
  private Long examId;
  private LocalDateTime finishedAt;
  private List<ExaminationAnswerEntity> answers = new ArrayList<>();
}
