package com.example.astraapi.mapper;

import com.example.astraapi.dto.test.ExaminationTestDto;
import com.example.astraapi.dto.test.RequestTestDto;
import com.example.astraapi.dto.test.TestFullDetailDto;
import com.example.astraapi.dto.test.TestShortDetailDto;
import com.example.astraapi.dto.test.TestingShortTestDto;
import com.example.astraapi.dto.test.TrainingTestDto;
import com.example.astraapi.entity.TestEntity;
import com.example.astraapi.entity.TestFullDetailEntity;
import com.example.astraapi.entity.TestShortDetailEntity;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import com.example.astraapi.model.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
    TitleQualifier.class,
    TestVariantMapper.class
})
public interface TestMapper {
  @Mapping(target = "question", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "comment", qualifiedByName = TitleQualifier.TRIM)
  TestEntity toEntity(RequestTestDto testDto);

  @Mapping(target = "question", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "comment", qualifiedByName = TitleQualifier.TRIM)
  TestEntity toEntity(Long id, RequestTestDto testDto);

  Page<TestShortDetailDto> toShortDetailDto(Page<TestShortDetailEntity> testShortDetailEntity);

  TestFullDetailDto toFullDetailDto(TestFullDetailEntity testFullDetailEntity);

  TrainingTestDto toTrainingDto(TestFullDetailEntity entity);

  ExaminationTestDto toExaminationDto(TestFullDetailEntity entity);

  TestingShortTestDto toShortTestDto(TestEntity entity);
}
