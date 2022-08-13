package com.example.astraapi.repository;

import com.example.astraapi.entity.SpecializationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SpecializationRepository {
  void save(@Param("entity") SpecializationEntity specializationEntity);

  List<SpecializationEntity> getAll();

  List<SpecializationEntity> getNotSelectedByExamId(@Param("examId") Long examId);
}
