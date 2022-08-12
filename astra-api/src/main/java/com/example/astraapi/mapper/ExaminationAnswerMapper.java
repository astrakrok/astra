package com.example.astraapi.mapper;

import com.example.astraapi.dto.ExaminationAnswerDto;
import com.example.astraapi.entity.ExaminationAnswerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExaminationAnswerMapper {
  ExaminationAnswerEntity toEntity(Long examinationId, Long testId);

  ExaminationAnswerEntity toEntity(ExaminationAnswerDto dto);

  ExaminationAnswerDto toDto(ExaminationAnswerEntity entity);
}
