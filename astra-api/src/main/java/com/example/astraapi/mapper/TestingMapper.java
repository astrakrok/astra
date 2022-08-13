package com.example.astraapi.mapper;

import com.example.astraapi.dto.RequestTestingDto;
import com.example.astraapi.dto.TestingWithSpecializationDto;
import com.example.astraapi.entity.TestingEntity;
import com.example.astraapi.entity.TestingWithSpecializationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestingMapper {
  TestingEntity toEntity(RequestTestingDto dto);

  TestingWithSpecializationDto toDto(TestingWithSpecializationEntity entity);
}
