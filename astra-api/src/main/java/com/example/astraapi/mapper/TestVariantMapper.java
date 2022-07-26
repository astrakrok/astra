package com.example.astraapi.mapper;

import com.example.astraapi.dto.TestVariantDto;
import com.example.astraapi.entity.TestVariantEntity;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TitleQualifier.class)
public interface TestVariantMapper {
  @Mapping(target = "title", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "explanation", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "isCorrect", source = "correct")
  TestVariantEntity toEntity(TestVariantDto testVariantDto);
}
