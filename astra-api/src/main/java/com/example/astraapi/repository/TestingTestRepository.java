package com.example.astraapi.repository;

import com.example.astraapi.entity.TestingTestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestingTestRepository {
  void save(@Param("entity") TestingTestEntity entity);

  boolean hasValidSpecialization(
      @Param("testingId") Long testingId,
      @Param("testId") Long testId
  );

  void deleteByTestingIdAndTestId(
      @Param("testingId") Long testingId,
      @Param("testId") Long testId);
}
