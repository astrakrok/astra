package com.example.astraapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubjectSpecializationRepository {
  void save(@Param("specializationIds") List<Long> specializationIds, @Param("subjectId") Long subjectId);
}
