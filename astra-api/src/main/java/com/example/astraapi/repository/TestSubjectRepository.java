package com.example.astraapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface TestSubjectRepository {
  void save(
      @Param("testId") Long testId,
      @Param("subjectIds") Collection<Long> subjectIds);
}
