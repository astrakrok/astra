package com.example.astraapi.repository;

import com.example.astraapi.entity.SubjectEntity;
import com.example.astraapi.entity.SubjectSpecializationHolder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubjectRepository {
  void save(@Param("entity") SubjectEntity subjectEntity);

  List<SubjectSpecializationHolder> getAll();
}
