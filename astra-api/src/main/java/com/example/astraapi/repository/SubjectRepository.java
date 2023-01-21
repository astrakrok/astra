package com.example.astraapi.repository;

import com.example.astraapi.entity.SubjectEntity;
import com.example.astraapi.entity.projection.ImportSubjectProjection;
import com.example.astraapi.entity.projection.SubjectDetailProjection;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubjectRepository {
    void save(@Param("entity") SubjectEntity subjectEntity);

    Page<SubjectDetailProjection> search(
            @Param("stepId") Long stepId,
            @Param("specializationId") Long specializationId,
            @Param("searchText") String searchText,
            @Param("pageable") Pageable pageable);

    void update(
            @Param("id") Long id,
            @Param("entity") SubjectEntity subjectEntity);

    List<ImportSubjectProjection> getSubjects(
            @Param("subjectTitles") List<String> subjectTitles,
            @Param("specializationTitles") List<String> specializationTitles,
            @Param("stepTitles") List<String> stepTitles);
}
