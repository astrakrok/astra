package com.example.astraapi.repository;

import com.example.astraapi.entity.TestEntity;
import com.example.astraapi.entity.TestFullDetailEntity;
import com.example.astraapi.entity.projection.TestShortDetailProjection;
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

    Optional<TestFullDetailEntity> getDetailedTestById(@Param("id") Long id);

    List<TestFullDetailEntity> getTestsByTestingId(
            @Param("testingId") Long testingId,
            @Param("count") Long count);

    List<TestFullDetailEntity> getTestsByIds(
            @Param("ids") List<Long> ids);

    List<TestEntity> getNotRelatedTestingTests(@Param("testingId") Long testingId);
}
