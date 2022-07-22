package com.example.astraapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectSpecializationHolder {
  private Long subjectId;
  private String subjectTitle;
  private List<SpecializationEntity> specializations;
}
