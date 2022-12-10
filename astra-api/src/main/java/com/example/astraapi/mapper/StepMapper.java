package com.example.astraapi.mapper;

import com.example.astraapi.dto.step.StepDto;
import com.example.astraapi.entity.StepEntity;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TitleQualifier.class)
public interface StepMapper {
  StepDto toDto(StepEntity entity);

  @Mapping(target = "title", qualifiedByName = TitleQualifier.TRIM)
  StepEntity toEntity(StepDto dto);
}
