package com.example.astraapi.mapper;

import com.example.astraapi.dto.testing.RequestTestingDto;
import com.example.astraapi.dto.testing.TestingDto;
import com.example.astraapi.dto.testing.TestingInfoDto;
import com.example.astraapi.dto.test.TestingTestQuestionDto;
import com.example.astraapi.dto.testing.TestingWithSpecializationDto;
import com.example.astraapi.entity.TestingEntity;
import com.example.astraapi.entity.TestingInfoEntity;
import com.example.astraapi.entity.TestingTestQuestionEntity;
import com.example.astraapi.entity.TestingWithSpecializationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestingMapper {
  TestingEntity toEntity(RequestTestingDto dto);

  TestingWithSpecializationDto toDto(TestingWithSpecializationEntity entity);

  TestingInfoDto toInfoDto(TestingInfoEntity entity);

  TestingTestQuestionDto toTestQuestionDto(TestingTestQuestionEntity entity);

  TestingDto toDto(TestingEntity entity);
}
