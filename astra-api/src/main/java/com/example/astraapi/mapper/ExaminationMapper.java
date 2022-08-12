package com.example.astraapi.mapper;

import com.example.astraapi.dto.ExaminationDto;
import com.example.astraapi.entity.ExaminationEntity;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = ExaminationAnswerMapper.class)
public interface ExaminationMapper {
  ExaminationEntity toEntity(
      Long userId,
      Long specializationId,
      Long examId,
      LocalDateTime finishedAt);

  ExaminationDto toDto(ExaminationEntity entity);
}
