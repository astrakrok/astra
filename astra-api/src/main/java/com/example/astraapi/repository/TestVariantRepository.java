package com.example.astraapi.repository;

import com.example.astraapi.entity.TestVariantEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Set;

@Mapper
public interface TestVariantRepository {
  void save(
      @Param("testId") Long testId,
      @Param("entities") Collection<TestVariantEntity> testVariantEntities);

  Set<Long> getVariantsIdsByTestId(@Param("testId") Long testId);

  void delete(
      @Param("testId") Long testId,
      @Param("variantsIds") Collection<Long> variantsIds);

  // We need to pass testId alongside with entity's id to guarantee that
  // variant with id=5 and test_id=7 cannot be updated from test with id=6
  void update(
      @Param("testId") Long testId,
      @Param("entity") TestVariantEntity entity);
}
