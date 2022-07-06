package com.example.astraapi.mapper;

import com.example.astraapi.dto.SpecializationDto;
import com.example.astraapi.entity.SpecializationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecializationMapper {
  SpecializationEntity toEntity(SpecializationDto specializationDto);
}
