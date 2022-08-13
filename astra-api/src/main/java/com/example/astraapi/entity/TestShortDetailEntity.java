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
public class TestShortDetailEntity {
  private Long id;
  private String question;
  private String comment;
  private List<SpecializationEntity> specializations;
}
