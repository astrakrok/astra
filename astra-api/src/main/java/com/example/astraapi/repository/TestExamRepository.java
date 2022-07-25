package com.example.astraapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface TestExamRepository {
  void save(
      @Param("testId") Long testId,
      @Param("examIds") Collection<Long> testIds);
}
