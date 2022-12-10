package com.example.astraapi.service;

import com.example.astraapi.dto.examination.ExaminationAnswerDto;
import com.example.astraapi.dto.examination.ExaminationResultDto;
import com.example.astraapi.dto.examination.ExaminationSearchDto;
import com.example.astraapi.dto.test.ExaminationTestDto;

import java.util.List;

public interface ExaminationAnswerService {
  List<ExaminationTestDto> createTestsForExamination(
      Long examinationId,
      ExaminationSearchDto searchDto,
      Integer count);

  List<ExaminationTestDto> getExaminationTests(List<ExaminationAnswerDto> answers);

  void updateAnswer(ExaminationAnswerDto examinationAnswerDto);

  ExaminationResultDto getResult(Long examinationId);
}
