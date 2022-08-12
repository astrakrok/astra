package com.example.astraapi.service;

import com.example.astraapi.dto.ExaminationAnswerDto;
import com.example.astraapi.dto.ExaminationResultDto;
import com.example.astraapi.dto.ExaminationSearchDto;
import com.example.astraapi.dto.ExaminationStateDto;

public interface ExaminationService {
  ExaminationStateDto start(ExaminationSearchDto searchDto);

  void updateAnswer(Long id, ExaminationAnswerDto examinationAnswerDto);

  ExaminationResultDto finish(Long id);
}
