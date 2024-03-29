package com.example.astraapi.repository;

import com.example.astraapi.entity.ExaminationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface ExaminationRepository {
    void save(@Param("entity") ExaminationEntity entity);

    Optional<ExaminationEntity> findExaminationWithAnswers(
            @Param("userId") Long userId,
            @Param("testingId") Long testingId,
            @Param("finishedAt") LocalDateTime finishedAt);

    boolean exists(
            @Param("id") Long id,
            @Param("userId") Long userId,
            @Param("finishedAtAfter") LocalDateTime finishedAtAfter);

    void updateFinishedAtById(
            @Param("id") Long id,
            @Param("finishedAt") LocalDateTime finishedAt);

    List<ExaminationEntity> getAllByUserIdAndFinishedAtBeforeOrderById(
            @Param("userId") Long userId,
            @Param("finishedAt") LocalDateTime finishedAt);

    void deleteById(@Param("id") Long id);
}
