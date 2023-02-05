package com.example.astraapi.mapper;

import com.example.astraapi.dto.examination.ExaminationStatisticDto;
import com.example.astraapi.dto.statistic.StepStatisticDto;
import com.example.astraapi.entity.ExaminationStatisticInfoEntity;
import com.example.astraapi.entity.projection.StepStatisticProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TestingMapper.class, SubjectMapper.class})
public interface ExaminationStatisticMapper {
    ExaminationStatisticDto toDto(ExaminationStatisticInfoEntity entity);

    StepStatisticDto toDto(StepStatisticProjection projection);
}
