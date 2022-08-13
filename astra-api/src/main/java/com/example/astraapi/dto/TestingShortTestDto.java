package com.example.astraapi.dto;

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
public class TestingShortTestDto {
  private Long id;
  private String question;
  private List<TestingShortTestVariantDto> variants = new ArrayList<>();
}
