package com.example.astraapi.mapper;

import com.example.astraapi.dto.test.*;
import com.example.astraapi.entity.TestEntity;
import com.example.astraapi.entity.TestFullDetailEntity;
import com.example.astraapi.entity.projection.TestShortDetailProjection;
import com.example.astraapi.entity.projection.exporting.ExportTestProjection;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import com.example.astraapi.meta.TestStatus;
import com.example.astraapi.model.exporting.ExportTest;
import com.example.astraapi.model.importing.ImportTest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        TitleQualifier.class,
        TestVariantMapper.class
})
public interface TestMapper {
    @Mapping(target = "question", qualifiedByName = TitleQualifier.TRIM)
    @Mapping(target = "comment", qualifiedByName = TitleQualifier.TRIM)
    TestEntity toEntity(RequestTestDto testDto, TestStatus status);

    @Mapping(target = "question", qualifiedByName = TitleQualifier.TRIM)
    @Mapping(target = "comment", qualifiedByName = TitleQualifier.TRIM)
    TestEntity toEntity(Long id, RequestTestDto testDto, TestStatus status);

    TestShortDetailDto toShortDetailDto(TestShortDetailProjection testShortDetailProjection);

    TestFullDetailDto toFullDetailDto(TestFullDetailEntity testFullDetailEntity);

    TrainingTestDto toTrainingDto(TestFullDetailEntity entity);

    ExaminationTestDto toExaminationDto(TestFullDetailEntity entity);

    TestingShortTestDto toShortTestDto(TestEntity entity);

    RequestTestDto toRequestTestDto(ImportTest test, List<Long> subjectIds);

    ExportTest toExportTest(ExportTestProjection projection);
}
