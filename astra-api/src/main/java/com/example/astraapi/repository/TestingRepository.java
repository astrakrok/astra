package com.example.astraapi.repository;

import com.example.astraapi.entity.TestingEntity;
import com.example.astraapi.entity.TestingWithSpecializationEntity;
import com.example.astraapi.entity.projection.TestingInfoProjection;
import com.example.astraapi.entity.projection.TestingTestSimpleProjection;
import com.example.astraapi.meta.TestingStatus;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.model.TestingPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mapper
public interface TestingRepository {
    void save(@Param("entity") TestingEntity entity);

    List<TestingWithSpecializationEntity> getByExamIdWithSpecialization(@Param("examId") Long examId);

    Optional<TestingInfoProjection> findTestingInfoById(@Param("id") Long id);

    TestingPage<TestingTestSimpleProjection> getTestingTestsByTestingId(
            @Param("testingId") Long testingId,
            @Param("searchText") String searchText,
            @Param("pageable") Pageable pageable);

    List<TestingEntity> getAvailable();

    TestingEntity getByExamIdAndSpecializationId(
            @Param("examId") Long examId,
            @Param("specializationId") Long specializationId);

    Long getTestsCount(@Param("id") Long id);

    void updateStatusById(
            @Param("id") Long id,
            @Param("status") TestingStatus status);

    List<TestingInfoProjection> getRedundantTestings(
            @Param("testId") Long testId,
            @Param("subjectsIds") Set<Long> subjectsIds);
}
