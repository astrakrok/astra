package com.example.astraapi.repository;

import com.example.astraapi.entity.ExaminationAnswerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExaminationAnswerRepository {
  void saveAll(@Param("entities") List<ExaminationAnswerEntity> entities);

  List<ExaminationAnswerEntity> getAllByExaminationId(@Param("examinationId") Long examinationId);

  void update(@Param("entity") ExaminationAnswerEntity entity);
}
