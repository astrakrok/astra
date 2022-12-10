package com.example.astraapi.mapper;

import com.example.astraapi.dto.test.AnsweredTestDto;
import com.example.astraapi.dto.examination.ExaminationAnswerDto;
import com.example.astraapi.entity.ExaminationAnswerEntity;
import com.example.astraapi.entity.TestFullDetailEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExaminationAnswerMapper {
  ExaminationAnswerEntity toEntity(Long examinationId, Long testId);

  ExaminationAnswerEntity toEntity(ExaminationAnswerDto dto);

  ExaminationAnswerDto toDto(ExaminationAnswerEntity entity);

  AnsweredTestDto toAnsweredTestDto(TestFullDetailEntity test, Long userAnswer);
}
