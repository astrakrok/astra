package com.example.astraapi.mapper;

import com.example.astraapi.dto.ResponseSubjectDto;
import com.example.astraapi.dto.RequestSubjectDto;
import com.example.astraapi.entity.SubjectEntity;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TitleQualifier.class)
public interface SubjectMapper {
  @Mapping(source = "specializationId", target = "specializationId")
  @Mapping(target = "title", qualifiedByName = TitleQualifier.TRIM)
  SubjectEntity toEntity(Long specializationId, RequestSubjectDto requestSubjectDto);

  ResponseSubjectDto toDto(SubjectEntity subjectEntity);
}
