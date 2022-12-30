package com.example.astraapi.dto.test;

import com.example.astraapi.dto.specialization.SpecializationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestShortDetailDto {
  private Long id;
  private String question;
  private String comment;
  private List<SpecializationDto> specializations;
}