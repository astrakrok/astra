package com.example.astraapi.mapper;

import com.example.astraapi.dto.importing.ImportStatsDto;
import com.example.astraapi.entity.projection.ImportStatsProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImportMapper {
    ImportStatsDto toDto(ImportStatsProjection projection);
}
