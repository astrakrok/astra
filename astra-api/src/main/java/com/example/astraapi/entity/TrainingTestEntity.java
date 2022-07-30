package com.example.astraapi.entity;

import com.example.astraapi.dto.TrainingVariantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingTestEntity {
  private Long id;
  private String question;
  private String comment;
  private List<TrainingVariantDto> variants;
}
