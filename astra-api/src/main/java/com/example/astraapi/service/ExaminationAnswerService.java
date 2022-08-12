package com.example.astraapi.service;

import com.example.astraapi.dto.ExaminationAnswerDto;
import com.example.astraapi.dto.ExaminationSearchDto;
import com.example.astraapi.dto.ExaminationTestDto;

import java.util.List;

public interface ExaminationAnswerService {
  List<ExaminationTestDto> createTestsForExamination(
      Long examinationId,
      ExaminationSearchDto searchDto,
      Integer count);

  List<ExaminationTestDto> getExaminationTests(List<ExaminationAnswerDto> answers);

  void updateAnswer(ExaminationAnswerDto examinationAnswerDto);
}
