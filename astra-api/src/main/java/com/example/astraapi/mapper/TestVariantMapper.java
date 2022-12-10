package com.example.astraapi.mapper;

import com.example.astraapi.dto.examination.ExaminationVariantDto;
import com.example.astraapi.dto.test.variant.TestVariantDto;
import com.example.astraapi.dto.test.variant.TrainingVariantDto;
import com.example.astraapi.entity.TestVariantEntity;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TitleQualifier.class)
public interface TestVariantMapper {
  @Mapping(target = "title", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "explanation", qualifiedByName = TitleQualifier.TRIM)
  TestVariantEntity toEntity(TestVariantDto testVariantDto);

  TrainingVariantDto toTrainingDto(TestVariantEntity entity);

  ExaminationVariantDto toExaminationDto(TestVariantEntity entity);
}
