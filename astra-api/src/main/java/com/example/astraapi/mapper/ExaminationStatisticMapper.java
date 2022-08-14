package com.example.astraapi.mapper;

import com.example.astraapi.dto.ExaminationStatisticDto;
import com.example.astraapi.entity.ExaminationStatisticInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TestingMapper.class)
public interface ExaminationStatisticMapper {
  @Mapping(target = "best", source = "bestPercentage")
  @Mapping(target = "last", source = "lastPercentage")
  ExaminationStatisticDto toDto(ExaminationStatisticInfoEntity entity);
}
