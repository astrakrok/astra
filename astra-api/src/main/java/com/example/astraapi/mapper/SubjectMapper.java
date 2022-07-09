package com.example.astraapi.mapper;

import com.example.astraapi.dto.ResponseSubjectDto;
import com.example.astraapi.dto.RequestSubjectDto;
import com.example.astraapi.entity.SubjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
  @Mapping(source = "specializationId", target = "specializationId")
  SubjectEntity toEntity(Long specializationId, RequestSubjectDto requestSubjectDto);

  ResponseSubjectDto toDto(SubjectEntity subjectEntity);
}
