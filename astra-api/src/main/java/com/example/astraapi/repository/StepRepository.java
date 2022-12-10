package com.example.astraapi.repository;

import com.example.astraapi.entity.StepEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StepRepository {
  void save(@Param("entity") StepEntity entity);

  List<StepEntity> getAll();
}
