package com.example.astraapi.mapper;

import com.example.astraapi.dto.examination.ExaminationDto;
import com.example.astraapi.entity.ExaminationEntity;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = ExaminationAnswerMapper.class)
public interface ExaminationMapper {
  ExaminationEntity toEntity(
      Long userId,
      Long testingId,
      LocalDateTime finishedAt);

  ExaminationDto toDto(ExaminationEntity entity);
}
