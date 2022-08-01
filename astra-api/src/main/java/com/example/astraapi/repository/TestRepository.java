package com.example.astraapi.repository;

import com.example.astraapi.entity.TestEntity;
import com.example.astraapi.entity.TestShortDetailEntity;
import com.example.astraapi.entity.TestingTestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestRepository {
  void save(@Param("entity") TestEntity testEntity);

  List<TestShortDetailEntity> getAll();

  List<TestingTestEntity> getTestingBySpecializationIdAndGoodId(
      @Param("specializationId") Long specializationId,
      @Param("examId") Long examId,
      @Param("count") Long count);
}
