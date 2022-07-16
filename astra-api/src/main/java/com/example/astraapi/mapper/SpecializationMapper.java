package com.example.astraapi.mapper;

import com.example.astraapi.dto.SpecializationDto;
import com.example.astraapi.entity.SpecializationEntity;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TitleQualifier.class)
public interface SpecializationMapper {
  @Mapping(target = "title", qualifiedByName = TitleQualifier.TRIM)
  SpecializationEntity toEntity(SpecializationDto specializationDto);

  SpecializationDto toDto(SpecializationEntity specializationEntity);
}
