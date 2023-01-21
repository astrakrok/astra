package com.example.astraapi.repository;

import com.example.astraapi.entity.ExaminationStatisticInfoEntity;
import com.example.astraapi.entity.projection.ExaminationStatisticProjection;
import com.example.astraapi.entity.projection.StepStatisticProjection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ExaminationStatisticRepository {
    Optional<ExaminationStatisticProjection> calculateStatistic(@Param("examinationId") Long examinationId);

    List<ExaminationStatisticInfoEntity> getAllWithTestingByUserId(@Param("userId") Long userId);

    List<StepStatisticProjection> getStepsStatisticByUserId(@Param("userId") Long userId);
}
