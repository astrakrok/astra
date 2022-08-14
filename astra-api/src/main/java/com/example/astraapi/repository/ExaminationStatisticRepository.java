package com.example.astraapi.repository;

import com.example.astraapi.entity.ExaminationStatisticEntity;
import com.example.astraapi.entity.ExaminationStatisticInfoEntity;
import com.example.astraapi.entity.projection.ExaminationStatisticProjection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ExaminationStatisticRepository {
  Optional<ExaminationStatisticProjection> calculateStatistic(@Param("examinationId") Long examinationId);

  Optional<ExaminationStatisticEntity> findByUserIdAndTestingId(
      @Param("userId") Long userId,
      @Param("testingId") Long testingId);

  void save(@Param("entity") ExaminationStatisticEntity entity);

  void updatePercentage(@Param("entity") ExaminationStatisticEntity entity);

  List<ExaminationStatisticInfoEntity> getAllWithTestingByUserId(@Param("userId") Long userId);
}
