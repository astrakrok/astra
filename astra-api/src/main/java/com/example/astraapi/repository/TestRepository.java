package com.example.astraapi.repository;

import com.example.astraapi.entity.TestEntity;
import com.example.astraapi.entity.TestFullDetailEntity;
import com.example.astraapi.entity.TestShortDetailEntity;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TestRepository {
  void save(@Param("entity") TestEntity testEntity);

  Page<TestShortDetailEntity> getAll(@Param("pageable") Pageable pageable);

  void update(@Param("entity") TestEntity testEntity);

  Optional<TestFullDetailEntity> getDetailedTestById(@Param("id") Long id);

  List<TestFullDetailEntity> getTestsByTestingId(
      @Param("testingId") Long testingId,
      @Param("count") Long count);

  List<TestFullDetailEntity> getTestsByIds(
      @Param("ids") List<Long> ids);

  List<TestEntity> getNotRelatedTestingTests(@Param("testingId") Long testingId);
}
