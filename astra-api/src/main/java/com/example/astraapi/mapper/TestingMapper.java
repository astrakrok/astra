package com.example.astraapi.mapper;

import com.example.astraapi.dto.test.RequestTestingDetailTestDto;
import com.example.astraapi.dto.test.TestingTestQuestionDto;
import com.example.astraapi.dto.testing.*;
import com.example.astraapi.entity.TestingEntity;
import com.example.astraapi.entity.TestingWithSpecializationEntity;
import com.example.astraapi.entity.projection.TestingInfoProjection;
import com.example.astraapi.entity.projection.TestingTestSimpleProjection;
import com.example.astraapi.meta.TestingStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestingMapper {
    TestingEntity toEntity(RequestTestingDto dto, TestingStatus status);

    TestingEntity toEntity(RequestTestingDetailTestDto dto, TestingStatus status);

    TestingWithSpecializationDto toDto(TestingWithSpecializationEntity entity);

    TestingDto toDto(TestingEntity entity);

    TestingDetailDto toDetailDto(TestingEntity entity);

    TestingDetailDto toDetailDto(TestingInfoProjection projection);

    TestingInfoDto toInfoDto(TestingInfoProjection entity);

    @Mapping(target = "id", source = "testingTestId")
    @Mapping(target = "testId", source = "testId")
    @Mapping(target = "testQuestion", source = "question")
    TestingTestQuestionDto toTestQuestionDto(TestingTestSimpleProjection projection);
}
