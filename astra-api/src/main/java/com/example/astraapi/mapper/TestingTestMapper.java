package com.example.astraapi.mapper;

import com.example.astraapi.dto.RequestTestingTestDto;
import com.example.astraapi.entity.TestingTestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestingTestMapper {
  TestingTestEntity toEntity(RequestTestingTestDto dto);
}
