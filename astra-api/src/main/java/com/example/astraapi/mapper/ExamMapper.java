package com.example.astraapi.mapper;

import com.example.astraapi.dto.exam.RequestExamDto;
import com.example.astraapi.dto.exam.ResponseExamDto;
import com.example.astraapi.entity.ExamEntity;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TitleQualifier.class)
public interface ExamMapper {
  @Mapping(target = "title", qualifiedByName = TitleQualifier.TRIM)
  ExamEntity toEntity(RequestExamDto examDto);

  ResponseExamDto toDto(ExamEntity examEntity);
}
