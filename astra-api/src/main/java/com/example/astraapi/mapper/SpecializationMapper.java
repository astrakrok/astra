package com.example.astraapi.mapper;

import com.example.astraapi.dto.SpecializationDto;
import com.example.astraapi.entity.Specialization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecializationMapper {

  Specialization dtoToModel(SpecializationDto specializationDto);
}
