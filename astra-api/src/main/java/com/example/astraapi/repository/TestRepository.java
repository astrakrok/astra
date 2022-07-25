package com.example.astraapi.repository;

import com.example.astraapi.entity.TestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestRepository {
  void save(@Param("entity") TestEntity testEntity);
}
