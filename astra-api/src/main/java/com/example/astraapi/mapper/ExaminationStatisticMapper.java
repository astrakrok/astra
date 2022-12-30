package com.example.astraapi.mapper;

import com.example.astraapi.dto.examination.ExaminationStatisticDto;
import com.example.astraapi.entity.ExaminationStatisticInfoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TestingMapper.class)
public interface ExaminationStatisticMapper {
  ExaminationStatisticDto toDto(ExaminationStatisticInfoEntity entity);
}
