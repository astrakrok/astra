package com.example.astraapi.dto.test;

import com.example.astraapi.dto.test.variant.TrainingVariantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingTestDto {
  private Long id;
  private String question;
  private String questionSvg;
  private String comment;
  private String commentSvg;
  private List<TrainingVariantDto> variants;
}
