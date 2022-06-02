package com.example.astraapi.repository;

import com.example.astraapi.TestEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestRepository {
  TestEntity findOne();
}
