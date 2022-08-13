package com.example.astraapi.repository;

import com.example.astraapi.entity.TestingEntity;
import com.example.astraapi.entity.TestingWithSpecializationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestingRepository {
  void save(@Param("entity") TestingEntity entity);

  List<TestingWithSpecializationEntity> getByExamIdWithSpecialization(@Param("examId") Long examId);
}
