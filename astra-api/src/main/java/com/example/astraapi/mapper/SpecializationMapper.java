package com.example.astraapi.mapper;

import com.example.astraapi.dto.SpecializationDto;
import com.example.astraapi.entity.SpecializationEntity;
import com.example.astraapi.mapper.qualifier.SpecializationQualifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = SpecializationQualifier.class)
public interface SpecializationMapper {
  @Mapping(target = "title", qualifiedByName = SpecializationQualifier.TRIM)
  SpecializationEntity toEntity(SpecializationDto specializationDto);

  SpecializationDto toDto(SpecializationEntity specializationEntity);
}
