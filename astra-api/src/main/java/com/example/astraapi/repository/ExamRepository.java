package com.example.astraapi.repository;

import com.example.astraapi.entity.ExamEntity;
import com.example.astraapi.meta.TestingStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExamRepository {
    void save(@Param("entity") ExamEntity examEntity);

    List<ExamEntity> getAll();

    List<ExamEntity> getAllBySpecializationIdAndStatus(
            @Param("specializationId") Long specializationId,
            @Param("status") TestingStatus status);

    void deleteById(@Param("id") Long id);

    void updateById(
            @Param("id") Long id,
            @Param("entity") ExamEntity examEntity);

    List<ExamEntity> getAllByStepId(@Param("stepId") Long stepId);
}
