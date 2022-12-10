package com.example.astraapi.repository;

import com.example.astraapi.entity.SpecializationEntity;
import com.example.astraapi.entity.projection.StepSpecializationProjection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SpecializationRepository {
  void save(@Param("entity") SpecializationEntity specializationEntity);

  List<StepSpecializationProjection> getAllWithSteps();

  List<SpecializationEntity> getAllByStepId(@Param("stepId") Long stepId);

  List<SpecializationEntity> getNotSelectedByExamId(@Param("examId") Long examId);
}
