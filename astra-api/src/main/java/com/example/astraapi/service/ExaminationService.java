package com.example.astraapi.service;

import com.example.astraapi.dto.examination.ExaminationAnswerDto;
import com.example.astraapi.dto.examination.ExaminationResultDto;
import com.example.astraapi.dto.examination.ExaminationSearchDto;
import com.example.astraapi.dto.examination.ExaminationStateDto;

public interface ExaminationService {
    ExaminationStateDto start(ExaminationSearchDto searchDto);

    void updateAnswer(Long id, ExaminationAnswerDto examinationAnswerDto);

    ExaminationResultDto finish(Long id);
}
