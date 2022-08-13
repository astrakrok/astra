package com.example.astraapi.repository;

import com.example.astraapi.entity.TestEntity;
import com.example.astraapi.entity.TestFullDetailEntity;
import com.example.astraapi.entity.TestShortDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TestRepository {
  void save(@Param("entity") TestEntity testEntity);

  List<TestShortDetailEntity> getAll();

  Optional<TestFullDetailEntity> getDetailedTestById(@Param("id") Long id);

  List<TestFullDetailEntity> getTestingBySpecializationIdAndExamId(
      @Param("specializationId") Long specializationId,
      @Param("examId") Long examId,
      @Param("count") Long count);

  List<TestFullDetailEntity> getTestsByIds(
      @Param("ids") List<Long> ids);

  List<TestEntity> getNotRelatedTestingTests(@Param("testingId") Long testingId);
}
