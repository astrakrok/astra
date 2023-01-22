package com.example.astraapi.repository;

import com.example.astraapi.entity.TestingTestEntity;
import com.example.astraapi.entity.projection.TestingInfoProjection;
import com.example.astraapi.meta.TestingStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestingTestRepository {
    void save(@Param("entity") TestingTestEntity entity);

    boolean hasValidSpecialization(
            @Param("testingId") Long testingId,
            @Param("testId") Long testId);

    boolean hasTestingStatus(
            @Param("id") Long id,
            @Param("status") TestingStatus status);

    boolean existsByTestingIdAndTestId(
            @Param("testingId") Long testingId,
            @Param("testId") Long testId);

    void deleteById(@Param("id") Long id);

    void deleteByTestingIdAndTestId(
            @Param("testingId") Long testingId,
            @Param("testId") Long testId);

    List<TestingInfoProjection> getTestings(@Param("testId") Long testId);
}
