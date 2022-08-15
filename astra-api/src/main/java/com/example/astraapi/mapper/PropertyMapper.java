package com.example.astraapi.mapper;

import com.example.astraapi.entity.PropertyEntity;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TitleQualifier.class)
public interface PropertyMapper {
  @Mapping(target = "value", qualifiedByName = TitleQualifier.TRIM)
  PropertyEntity toEntity(String name, String value);
}
