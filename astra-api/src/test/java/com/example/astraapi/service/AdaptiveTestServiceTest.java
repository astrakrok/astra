package com.example.astraapi.service;

import com.example.astraapi.config.AdaptiveProperties;
import com.example.astraapi.dto.statistic.SpecializationStatisticDto;
import com.example.astraapi.dto.statistic.StatisticDto;
import com.example.astraapi.dto.statistic.StepStatisticDto;
import com.example.astraapi.dto.statistic.SubjectStatisticDto;
import com.example.astraapi.dto.test.AdaptiveTestDto;
import com.example.astraapi.entity.projection.TestFullDetailProjection;
import com.example.astraapi.entity.projection.TestIdAndSubjectsIdsProjection;
import com.example.astraapi.mapper.TestMapper;
import com.example.astraapi.mapper.TestVariantMapper;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import com.example.astraapi.meta.TestStatus;
import com.example.astraapi.repository.TestRepository;
import com.example.astraapi.service.impl.AdaptiveTestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdaptiveTestServiceTest {
    @InjectMocks
    private AdaptiveTestServiceImpl adaptiveTestService;
    @Mock
    private StatisticService statisticService;
    @Mock
    private TestMapper testMapper;
    @Mock
    private TestRepository testRepository;
    @Spy
    private AdaptiveProperties adaptiveProperties;

    @BeforeEach
    public void beforeEach() {
        TestMapper mapper = Mappers.getMapper(TestMapper.class);
        TestVariantMapper testVariantMapper = Mappers.getMapper(TestVariantMapper.class);
        TitleQualifier titleQualifier = new TitleQualifier();
        ReflectionTestUtils.setField(testVariantMapper, "titleQualifier", titleQualifier);
        ReflectionTestUtils.setField(mapper, "titleQualifier", titleQualifier);
        ReflectionTestUtils.setField(mapper, "testVariantMapper", testVariantMapper);

        lenient().when(testMapper.toEntity(any(), any()))
                .thenAnswer(invocation -> mapper.toEntity(invocation.getArgument(0), invocation.getArgument(1)));
        lenient().when(testMapper.toEntity(any(), any(), any()))
                .thenAnswer(invocation -> mapper.toEntity(invocation.getArgument(0), invocation.getArgument(1), invocation.getArgument(2)));
        lenient().when(testMapper.toShortDetailDto(any()))
                .thenAnswer(invocation -> mapper.toShortDetailDto(invocation.getArgument(0)));
        lenient().when(testMapper.toFullDetailDto(any()))
                .thenAnswer(invocation -> mapper.toFullDetailDto(invocation.getArgument(0)));
        lenient().when(testMapper.toTrainingDto(any()))
                .thenAnswer(invocation -> mapper.toTrainingDto(invocation.getArgument(0)));
        lenient().when(testMapper.toExaminationDto(any()))
                .thenAnswer(invocation -> mapper.toExaminationDto(invocation.getArgument(0)));
        lenient().when(testMapper.toShortTestDto(any()))
                .thenAnswer(invocation -> mapper.toShortTestDto(invocation.getArgument(0)));
        lenient().when(testMapper.toRequestTestDto(any(), any()))
                .thenAnswer(invocation -> mapper.toRequestTestDto(invocation.getArgument(0), invocation.getArgument(1)));
        lenient().when(testMapper.toExportTest(any()))
                .thenAnswer(invocation -> mapper.toExportTest(invocation.getArgument(0)));
        lenient().when(testMapper.toAdaptiveTestDto(any()))
                .thenAnswer(invocation -> mapper.toAdaptiveTestDto(invocation.getArgument(0)));
    }

    @Test
    void shouldReturnAdaptiveTests() {
        adaptiveProperties.setSubjectsCount(3);
        adaptiveProperties.setCount(10);
        when(statisticService.getStepsStatistic(any())).thenReturn(List.of(
                new StepStatisticDto(
                        1L,
                        "step 1",
                        List.of(
                                new SpecializationStatisticDto(
                                        1L,
                                        "specialization 1",
                                        List.of(
                                                new SubjectStatisticDto(1L, "subject 1", new StatisticDto(5L, 8L)),
                                                new SubjectStatisticDto(2L, "subject 2", new StatisticDto(0L, 10L)))),
                                new SpecializationStatisticDto(
                                        2L,
                                        "specialization 2",
                                        List.of(
                                                new SubjectStatisticDto(3L, "subject 3", new StatisticDto(5L, 8L)),
                                                new SubjectStatisticDto(4L, "subject 4", new StatisticDto(2L, 10L)),
                                                new SubjectStatisticDto(5L, "subject 5", new StatisticDto(8L, 8L)))))),
                new StepStatisticDto(
                        2L,
                        "step 2",
                        List.of(
                                new SpecializationStatisticDto(
                                        3L,
                                        "specialization 3",
                                        List.of(
                                                new SubjectStatisticDto(6L, "subject 6", new StatisticDto(0L, 0L))))))
        ));
        when(testRepository.getActiveTestsIdsBySubjectAndExceptIds(eq(List.of(2L)), any(), eq(5L))).thenReturn(List.of(
                new TestIdAndSubjectsIdsProjection(1L, List.of(2L, 1L, 4L)),
                new TestIdAndSubjectsIdsProjection(2L, List.of(2L, 4L)),
                new TestIdAndSubjectsIdsProjection(3L, List.of(2L)),
                new TestIdAndSubjectsIdsProjection(4L, List.of(2L)),
                new TestIdAndSubjectsIdsProjection(5L, List.of(2L, 6L))
        ));
        when(testRepository.getActiveTestsIdsBySubjectAndExceptIds(eq(List.of(4L)), any(), eq(2L))).thenReturn(List.of(
                new TestIdAndSubjectsIdsProjection(6L, List.of(4L, 1L)),
                new TestIdAndSubjectsIdsProjection(7L, List.of(4L, 5L, 6L))
        ));
        when(testRepository.getActiveTestsIdsBySubjectAndExceptIds(argThat(arg -> arg.size() > 0), any(), eq(3L))).thenReturn(List.of(
                new TestIdAndSubjectsIdsProjection(8L, List.of(1L)),
                new TestIdAndSubjectsIdsProjection(9L, List.of(2L)),
                new TestIdAndSubjectsIdsProjection(10L, List.of(3L))
        ));
        when(testRepository.getFullDetailedTestsByIds(argThat(arg -> arg.size() == 10))).thenReturn(List.of(
                new TestFullDetailProjection(1L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()),
                new TestFullDetailProjection(2L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()),
                new TestFullDetailProjection(3L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()),
                new TestFullDetailProjection(4L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()),
                new TestFullDetailProjection(5L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()),
                new TestFullDetailProjection(6L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()),
                new TestFullDetailProjection(7L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()),
                new TestFullDetailProjection(8L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()),
                new TestFullDetailProjection(9L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()),
                new TestFullDetailProjection(10L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap())
        ));

        List<AdaptiveTestDto> items = adaptiveTestService.getAdaptiveTests(5L);

        assertEquals(10L, items.size());
    }

    @Test
    void shouldReturnRandomTestsWhenNoFailedTests() {
        adaptiveProperties.setSubjectsCount(2);
        adaptiveProperties.setCount(4);
        when(statisticService.getStepsStatistic(any())).thenReturn(List.of(
                new StepStatisticDto(
                        1L,
                        "step 1",
                        List.of(
                                new SpecializationStatisticDto(
                                        1L,
                                        "specialization 1",
                                        List.of(
                                                new SubjectStatisticDto(1L, "subject 1", new StatisticDto(0L, 0L)),
                                                new SubjectStatisticDto(2L, "subject 2", new StatisticDto(10L, 10L))))))
        ));
        when(testRepository.getActiveTestsIdsBySubjectAndExceptIds(argThat(arg -> arg.size() > 0), any(), eq(4L))).thenReturn(List.of(
                new TestIdAndSubjectsIdsProjection(1L, List.of(1L)),
                new TestIdAndSubjectsIdsProjection(2L, List.of(1L)),
                new TestIdAndSubjectsIdsProjection(3L, List.of(1L)),
                new TestIdAndSubjectsIdsProjection(4L, List.of(1L))
        ));
        when(testRepository.getFullDetailedTestsByIds(argThat(arg -> arg.size() == 4))).thenReturn(List.of(
                new TestFullDetailProjection(1L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()),
                new TestFullDetailProjection(2L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()),
                new TestFullDetailProjection(3L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()),
                new TestFullDetailProjection(4L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap())
        ));

        List<AdaptiveTestDto> items = adaptiveTestService.getAdaptiveTests(5L);

        assertEquals(4L, items.size());
    }
}
