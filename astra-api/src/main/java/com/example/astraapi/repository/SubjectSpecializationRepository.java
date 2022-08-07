package com.example.astraapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface SubjectSpecializationRepository {
  void save(
      @Param("specializationIds") Set<Long> specializationIds,
      @Param("subjectId") Long subjectId);

  void delete(
      @Param("specializationIds") Set<Long> specializationIds,
      @Param("subjectId") Long subjectId);

  Set<Long> getSpecializationIdBySubjectId(@Param("subjectId") Long subjectId);
}
