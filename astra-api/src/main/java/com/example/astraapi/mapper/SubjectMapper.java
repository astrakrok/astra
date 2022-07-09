package com.example.astraapi.mapper;

import com.example.astraapi.dto.SubjectAndSpecializationIdDto;
import com.example.astraapi.dto.SubjectDto;
import com.example.astraapi.entity.SubjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
  @Mapping(source = "specializationId", target = "specializationId")
  SubjectEntity toEntity(Long specializationId, SubjectDto subjectDto);

  //@Mapping(source = "specializationId", target = "specializationId")
  SubjectAndSpecializationIdDto toDto(SubjectEntity subjectEntity);
}
