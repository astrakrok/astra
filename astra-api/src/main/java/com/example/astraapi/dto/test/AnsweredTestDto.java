package com.example.astraapi.dto.test;

import com.example.astraapi.dto.test.variant.TestVariantDto;
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
public class AnsweredTestDto {
  private Long id;
  private String question;
  private String questionSvg;
  private String comment;
  private String commentSvg;
  private Long userAnswer;
  private List<TestVariantDto> variants = new ArrayList<>();
}
