package com.example.astraapi.repository;

import com.example.astraapi.entity.SubjectDetailEntity;
import com.example.astraapi.entity.SubjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubjectRepository {
  void save(@Param("entity") SubjectEntity subjectEntity);

  List<SubjectDetailEntity> getAll();

  void update(
      @Param("id") Long id,
      @Param("entity") SubjectEntity subjectEntity);
}
