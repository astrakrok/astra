package com.example.astraapi.repository;

import com.example.astraapi.entity.TestEntity;
import com.example.astraapi.entity.projection.TestFullDetailProjection;
import com.example.astraapi.entity.projection.TestIdAndSubjectsIdsProjection;
import com.example.astraapi.entity.projection.TestShortDetailProjection;
import com.example.astraapi.entity.projection.exporting.ExportTestProjection;
import com.example.astraapi.meta.TestStatus;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TestRepository {
    void save(@Param("entity") TestEntity testEntity);

    Page<TestShortDetailProjection> getAll(
            @Param("searchText") String searchText,
            @Param("status") TestStatus status,
            @Param("importId") Long importId,
            @Param("pageable") Pageable pageable);

    void update(@Param("entity") TestEntity testEntity);

    boolean existsByIdAndStatus(
            @Param("id") Long id,
            @Param("status") TestStatus status);

    Optional<TestFullDetailProjection> getDetailedTestById(@Param("id") Long id);

    List<TestFullDetailProjection> getTestsByTestingId(
            @Param("testingId") Long testingId,
            @Param("count") Long count);

    List<TestFullDetailProjection> getTestsByIds(
            @Param("ids") List<Long> ids);

    Page<TestEntity> getNotRelatedTestingTests(
            @Param("testingId") Long testingId,
            @Param("searchText") String searchText,
            @Param("pageable") Pageable pageable);

    void deleteById(Long id);

    Page<ExportTestProjection> getExportTests(
            @Param("specializationId") Long specializationId,
            @Param("pageable") Pageable pageable);

    List<TestFullDetailProjection> getFullDetailedTests(
            @Param("specializationId") Long specializationId);

    List<TestFullDetailProjection> getFullDetailedTestsByIds(@Param("ids") List<Long> ids);

    List<TestIdAndSubjectsIdsProjection> getActiveTestsIdsBySubjectAndExceptIds(
            @Param("subjectsIds") List<Long> subjectsIds,
            @Param("exceptIds") List<Long> exceptIds,
            @Param("limit") Long limit);
}
