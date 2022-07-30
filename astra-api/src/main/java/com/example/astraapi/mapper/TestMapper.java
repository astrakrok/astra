package com.example.astraapi.mapper;

import com.example.astraapi.dto.RequestTestDto;
import com.example.astraapi.dto.TestShortDetailDto;
import com.example.astraapi.dto.TrainingTestDto;
import com.example.astraapi.entity.TestEntity;
import com.example.astraapi.entity.TestShortDetailEntity;
import com.example.astraapi.entity.TrainingTestEntity;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
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

  TestShortDetailDto toShortDetailDto(TestShortDetailEntity testShortDetailEntity);

  TrainingTestDto toDto(TrainingTestEntity entity);
}
