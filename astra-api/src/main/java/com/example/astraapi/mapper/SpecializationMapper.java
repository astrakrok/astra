package com.example.astraapi.mapper;

import com.example.astraapi.dto.specialization.RequestSpecializationDto;
import com.example.astraapi.dto.specialization.SpecializationDto;
import com.example.astraapi.dto.specialization.StepSpecializationDto;
import com.example.astraapi.entity.SpecializationEntity;
import com.example.astraapi.entity.projection.StepSpecializationProjection;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TitleQualifier.class)
public interface SpecializationMapper {
  @Mapping(target = "title", qualifiedByName = TitleQualifier.TRIM)
  SpecializationEntity toEntity(SpecializationDto specializationDto);

  @Mapping(target = "title", qualifiedByName = TitleQualifier.TRIM)
  SpecializationEntity toEntity(Long stepId, RequestSpecializationDto specializationDto);

  StepSpecializationDto toDto(StepSpecializationProjection specializationEntity);

  SpecializationDto toDto(SpecializationEntity specializationEntity);
}
