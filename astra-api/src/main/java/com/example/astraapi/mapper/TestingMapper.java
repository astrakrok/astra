package com.example.astraapi.mapper;

import com.example.astraapi.dto.test.TestingTestQuestionDto;
import com.example.astraapi.dto.testing.*;
import com.example.astraapi.entity.TestingEntity;
import com.example.astraapi.entity.TestingInfoEntity;
import com.example.astraapi.entity.TestingTestQuestionEntity;
import com.example.astraapi.entity.TestingWithSpecializationEntity;
import com.example.astraapi.meta.TestingStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestingMapper {
  TestingEntity toEntity(RequestTestingDto dto, TestingStatus status);

  TestingWithSpecializationDto toDto(TestingWithSpecializationEntity entity);

  TestingInfoDto toInfoDto(TestingInfoEntity entity);

  TestingTestQuestionDto toTestQuestionDto(TestingTestQuestionEntity entity);

  TestingDetailDto toDetailDto(TestingEntity entity);
  
  TestingDto toDto(TestingEntity entity);
}
