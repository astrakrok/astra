package com.example.astraapi.entity;

import com.example.astraapi.meta.TestingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestingInfoEntity {
  private Long id;
  private Long testsCount;
  private TestingStatus status;
  private ExamEntity exam;
  private SpecializationEntity specialization;
}
