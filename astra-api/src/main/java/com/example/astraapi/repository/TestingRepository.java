package com.example.astraapi.repository;

import com.example.astraapi.entity.TestingEntity;
import com.example.astraapi.entity.TestingInfoEntity;
import com.example.astraapi.entity.TestingTestQuestionEntity;
import com.example.astraapi.entity.TestingWithSpecializationEntity;
import com.example.astraapi.meta.TestingStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TestingRepository {
    void save(@Param("entity") TestingEntity entity);

    List<TestingWithSpecializationEntity> getByExamIdWithSpecialization(@Param("examId") Long examId);

    Optional<TestingInfoEntity> findTestingInfoById(@Param("id") Long id);

    List<TestingTestQuestionEntity> getTestingTestsByTestingId(@Param("testingId") Long testingId);

    List<TestingEntity> getAvailable();

    TestingEntity getByExamIdAndSpecializationId(
            @Param("examId") Long examId,
            @Param("specializationId") Long specializationId);

    Long getTestsCount(@Param("id") Long id);

    void updateStatusById(
            @Param("id") Long id,
            @Param("status") TestingStatus status);
}
