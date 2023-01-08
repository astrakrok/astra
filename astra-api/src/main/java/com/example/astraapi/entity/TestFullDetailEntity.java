package com.example.astraapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestFullDetailEntity {
  private Long id;
  private String question;
  private String questionSvg;
  private String comment;
  private String commentSvg;
  private List<TestVariantEntity> variants = new ArrayList<>();
  private List<ExamEntity> exams = new ArrayList<>();
  private List<SubjectEntity> subjects = new ArrayList<>();
  private Map<String, Object> importDetails = new HashMap<>();
}
