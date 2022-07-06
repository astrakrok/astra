package com.example.astraapi.repository;

import com.example.astraapi.entity.SpecializationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SpecializationRepository {
  void save(@Param("entity") SpecializationEntity specializationEntity);
}
