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
public class TestFullDetailDto {
  private Long id;
  private String question;
  private String questionSvg;
  private String comment;
  private String commentSvg;
  private List<TestVariantDto> variants;
  private List<ResponseExamDto> exams;
  private List<ResponseSubjectDto> subjects;
}
