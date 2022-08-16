package com.example.astraapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Set;

@Mapper
public interface TestSubjectRepository {
  void save(
      @Param("testId") Long testId,
      @Param("subjectIds") Collection<Long> subjectIds);

  Set<Long> getSubjectsIdsByTestId(@Param("testId") Long testId);

  void delete(
      @Param("testId") Long testId,
      @Param("subjectsIds") Collection<Long> subjectsIds);
}
