package com.example.astraapi.dto.examination;

import com.example.astraapi.dto.test.AnsweredTestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ExaminationResultDto {
  private List<AnsweredTestDto> tests = new ArrayList<>();
  private Long correctCount;
  private Long total;
  @JsonProperty("isSuccess")
  private boolean success;
}
