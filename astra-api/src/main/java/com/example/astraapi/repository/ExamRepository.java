package com.example.astraapi.repository;

import com.example.astraapi.entity.ExamEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExamRepository {
  void save(@Param("entity") ExamEntity examEntity);

  List<ExamEntity> getAll();
}
