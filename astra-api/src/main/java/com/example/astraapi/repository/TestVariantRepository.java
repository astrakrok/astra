package com.example.astraapi.repository;

import com.example.astraapi.entity.TestVariantEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface TestVariantRepository {
  void save(
      @Param("testId") Long testId,
      @Param("entities") Collection<TestVariantEntity> testVariantEntities);
}
