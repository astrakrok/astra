package com.example.astraapi.service;

import com.example.astraapi.dto.examination.ExaminationAnswerDto;
import com.example.astraapi.dto.examination.ExaminationResultDto;
import com.example.astraapi.dto.examination.ExaminationSearchDto;
import com.example.astraapi.dto.examination.ExaminationVariantDto;
import com.example.astraapi.dto.test.ExaminationTestDto;
import com.example.astraapi.entity.ExaminationAnswerEntity;
import com.example.astraapi.entity.PropertyEntity;
import com.example.astraapi.entity.TestVariantEntity;
import com.example.astraapi.entity.projection.TestFullDetailProjection;
import com.example.astraapi.mapper.ExaminationAnswerMapper;
import com.example.astraapi.meta.ConfigProperty;
import com.example.astraapi.meta.TestStatus;
import com.example.astraapi.repository.ExaminationAnswerRepository;
import com.example.astraapi.service.impl.ExaminationAnswerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ExaminationAnswerServiceTest {
    @InjectMocks
    private ExaminationAnswerServiceImpl examinationAnswerService;
    @Mock
    private TestService testService;
    @Mock
    private PropertyService propertyService;
    @Spy
    private ExaminationAnswerMapper examinationAnswerMapper;
    @Mock
    private ExaminationAnswerRepository examinationAnswerRepository;

    @BeforeEach
    void beforeEach() {
        ExaminationAnswerMapper mapper = Mappers.getMapper(ExaminationAnswerMapper.class);

        Mockito.lenient().when(examinationAnswerMapper.toEntity(ArgumentMatchers.any())).thenAnswer(invocation -> {
            ExaminationAnswerDto answerDto = invocation.getArgument(0);
            return mapper.toEntity(answerDto);
        });

        Mockito.lenient().when(examinationAnswerMapper.toAnsweredTestDto(ArgumentMatchers.any(), ArgumentMatchers.any())).thenAnswer(invocation -> {
            TestFullDetailProjection testEntity = invocation.getArgument(0);
            Long userAnswer = invocation.getArgument(1);
            return mapper.toAnsweredTestDto(testEntity, userAnswer);
        });
    }

    @Test
    void shouldSaveAndReturnExaminationTests() {
        Mockito.when(testService.getExaminationTests(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenAnswer(invocation -> {
            long count = invocation.getArgument(0);
            return mockExaminationTests(count);
        });

        List<ExaminationTestDto> tests = examinationAnswerService.createTestsForExamination(1L, new ExaminationSearchDto());

        assertEquals(0, tests.size());
    }

    @Test
    void shouldReturnEmptyList() {
        Mockito.when(testService.getExaminationTests(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenAnswer(invocation -> {
            long count = invocation.getArgument(0);
            return mockExaminationTests(count);
        });

        List<ExaminationTestDto> tests = examinationAnswerService.createTestsForExamination(1L, new ExaminationSearchDto());

        assertEquals(0, tests.size());
    }

    @Test
    void shouldReturnExaminationTests() {
        Mockito.when(testService.getExaminationTests(ArgumentMatchers.any())).thenAnswer(invocation -> {
            List<Long> testsIds = invocation.getArgument(0);
            return testsIds.stream()
                    .map(this::mockExaminationTest)
                    .collect(Collectors.toList());
        });

        List<ExaminationAnswerDto> answers = List.of(
                mockExaminationAnswer(3L, 5L),
                mockExaminationAnswer(4L, null),
                mockExaminationAnswer(1L, 1L),
                mockExaminationAnswer(2L, 2L),
                mockExaminationAnswer(6L, null),
                mockExaminationAnswer(10L, 20L));

        List<ExaminationTestDto> tests = examinationAnswerService.getExaminationTests(answers);

        assertEquals(6, tests.size());
        assertEquals(5L, getVariantIdByTestId(answers, 3L));
        assertNull(getVariantIdByTestId(answers, 4L));
        assertEquals(1L, getVariantIdByTestId(answers, 1L));
        assertEquals(2L, getVariantIdByTestId(answers, 2L));
        assertNull(getVariantIdByTestId(answers, 6L));
        assertEquals(20L, getVariantIdByTestId(answers, 10L));
    }

    @Test
    void shouldThrowExceptionOnInvalidAnswersList() {
        List<ExaminationAnswerDto> answers = List.of(
                mockExaminationAnswer(3L, 5L),
                mockExaminationAnswer(3L, null),
                mockExaminationAnswer(1L, 1L),
                mockExaminationAnswer(2L, 2L),
                mockExaminationAnswer(6L, null),
                mockExaminationAnswer(10L, 20L));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> examinationAnswerService.getExaminationTests(answers));

        assertEquals("Some tests have more than 1 user answer!", exception.getMessage());
    }

    @Test
    void shouldUpdateAnswer() {
        ExaminationAnswerEntity[] answer = new ExaminationAnswerEntity[1];
        Mockito.doAnswer(invocation -> {
            ExaminationAnswerEntity entity = invocation.getArgument(0);
            answer[0] = entity;
            return null;
        }).when(examinationAnswerRepository).update(ArgumentMatchers.any());

        ExaminationAnswerDto examinationAnswerDto = new ExaminationAnswerDto(5L, 4L, 6L, 10L);

        examinationAnswerService.updateAnswer(examinationAnswerDto);

        assertEquals(5L, answer[0].getId());
        assertEquals(4L, answer[0].getExaminationId());
        assertEquals(6L, answer[0].getTestId());
        assertEquals(10L, answer[0].getVariantId());
    }

    @Test
    void shouldReturnSuccessExaminationResult() {
        List<ExaminationAnswerEntity> answerEntities = List.of(
                new ExaminationAnswerEntity(
                        1L,
                        2L,
                        3L,
                        1L,
                        new TestFullDetailProjection(
                                3L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(1L, 3L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(2L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(3L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(4L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(5L, 3L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        10L,
                        20L,
                        30L,
                        10L,
                        new TestFullDetailProjection(
                                30L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(10L, 30L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(20L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(30L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(40L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(50L, 30L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        100L,
                        200L,
                        300L,
                        100L,
                        new TestFullDetailProjection(
                                300L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(100L, 300L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(200L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(300L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(400L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(500L, 300L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        1000L,
                        2000L,
                        3000L,
                        1000L,
                        new TestFullDetailProjection(
                                3000L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(1000L, 3000L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(2000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(3000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(4000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(5000L, 3000L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        10000L,
                        20000L,
                        30000L,
                        10000L,
                        new TestFullDetailProjection(
                                30000L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(10000L, 30000L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(20000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(30000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(40000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(50000L, 30000L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                )
        );

        Mockito.when(examinationAnswerRepository.getDetailedAnswersByExaminationId(ArgumentMatchers.any())).thenReturn(answerEntities);

        Mockito.when(propertyService.getProperty(ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName())).thenReturn(Optional.of(new PropertyEntity(
                1L,
                ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName(),
                "70"
        )));

        ExaminationResultDto result = examinationAnswerService.getResult(4L);

        assertTrue(result.isSuccess());
        assertEquals(5, result.getCorrectCount());
        assertEquals(5, result.getTotal());
    }

    @Test
    void shouldReturnNotSuccessExaminationResult() {
        List<ExaminationAnswerEntity> answerEntities = List.of(
                new ExaminationAnswerEntity(
                        1L,
                        2L,
                        3L,
                        2L,
                        new TestFullDetailProjection(
                                3L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(1L, 3L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(2L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(3L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(4L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(5L, 3L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        10L,
                        20L,
                        30L,
                        10L,
                        new TestFullDetailProjection(
                                30L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(10L, 30L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(20L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(30L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(40L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(50L, 30L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        100L,
                        200L,
                        300L,
                        null,
                        new TestFullDetailProjection(
                                300L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(100L, 300L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(200L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(300L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(400L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(500L, 300L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        1000L,
                        2000L,
                        3000L,
                        1000L,
                        new TestFullDetailProjection(
                                3000L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(1000L, 3000L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(2000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(3000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(4000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(5000L, 3000L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        10000L,
                        20000L,
                        30000L,
                        10000L,
                        new TestFullDetailProjection(
                                30000L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(10000L, 30000L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(20000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(30000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(40000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(50000L, 30000L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                )
        );

        Mockito.when(examinationAnswerRepository.getDetailedAnswersByExaminationId(ArgumentMatchers.any())).thenReturn(answerEntities);

        Mockito.when(propertyService.getProperty(ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName())).thenReturn(Optional.of(new PropertyEntity(
                1L,
                ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName(),
                "70"
        )));

        ExaminationResultDto result = examinationAnswerService.getResult(4L);

        assertFalse(result.isSuccess());
        assertEquals(3, result.getCorrectCount());
        assertEquals(5, result.getTotal());
    }

    @Test
    void shouldReturnSuccessWhenCorrectnessIsEqualToPercentageThreshold() {
        List<ExaminationAnswerEntity> answerEntities = List.of(
                new ExaminationAnswerEntity(
                        1L,
                        2L,
                        3L,
                        null,
                        new TestFullDetailProjection(
                                3L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(1L, 3L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(2L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(3L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(4L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(5L, 3L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        10L,
                        20L,
                        30L,
                        10L,
                        new TestFullDetailProjection(
                                30L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(10L, 30L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(20L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(30L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(40L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(50L, 30L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        100L,
                        200L,
                        300L,
                        100L,
                        new TestFullDetailProjection(
                                300L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(100L, 300L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(200L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(300L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(400L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(500L, 300L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        1000L,
                        2000L,
                        3000L,
                        1000L,
                        new TestFullDetailProjection(
                                3000L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(1000L, 3000L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(2000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(3000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(4000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(5000L, 3000L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        10000L,
                        20000L,
                        30000L,
                        10000L,
                        new TestFullDetailProjection(
                                30000L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(10000L, 30000L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(20000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(30000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(40000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(50000L, 30000L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                )
        );

        Mockito.when(examinationAnswerRepository.getDetailedAnswersByExaminationId(ArgumentMatchers.any())).thenReturn(answerEntities);

        Mockito.when(propertyService.getProperty(ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName())).thenReturn(Optional.of(new PropertyEntity(
                1L,
                ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName(),
                "80"
        )));

        ExaminationResultDto result = examinationAnswerService.getResult(4L);

        assertTrue(result.isSuccess());
        assertEquals(4, result.getCorrectCount());
        assertEquals(5, result.getTotal());
    }

    @Test
    void shouldReturnNotSuccessWhenCorrectnessIsZero() {
        List<ExaminationAnswerEntity> answerEntities = List.of(
                new ExaminationAnswerEntity(
                        1L,
                        2L,
                        3L,
                        2L,
                        new TestFullDetailProjection(
                                3L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(1L, 3L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(2L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(3L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(4L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(5L, 3L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        10L,
                        20L,
                        30L,
                        20L,
                        new TestFullDetailProjection(
                                30L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(10L, 30L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(20L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(30L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(40L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(50L, 30L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        100L,
                        200L,
                        300L,
                        200L,
                        new TestFullDetailProjection(
                                300L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(100L, 300L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(200L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(300L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(400L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(500L, 300L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        1000L,
                        2000L,
                        3000L,
                        2000L,
                        new TestFullDetailProjection(
                                3000L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(1000L, 3000L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(2000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(3000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(4000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(5000L, 3000L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        10000L,
                        20000L,
                        30000L,
                        20000L,
                        new TestFullDetailProjection(
                                30000L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(10000L, 30000L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(20000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(30000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(40000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(50000L, 30000L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                )
        );

        Mockito.when(examinationAnswerRepository.getDetailedAnswersByExaminationId(ArgumentMatchers.any())).thenReturn(answerEntities);

        Mockito.when(propertyService.getProperty(ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName())).thenReturn(Optional.of(new PropertyEntity(
                1L,
                ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName(),
                "80"
        )));

        ExaminationResultDto result = examinationAnswerService.getResult(4L);

        assertFalse(result.isSuccess());
        assertEquals(0, result.getCorrectCount());
        assertEquals(5, result.getTotal());
    }

    @Test
    void shouldReturnNotSuccessWhenNoAnsweredTests() {
        List<ExaminationAnswerEntity> answerEntities = List.of(
                new ExaminationAnswerEntity(
                        1L,
                        2L,
                        3L,
                        null,
                        new TestFullDetailProjection(
                                3L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(1L, 3L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(2L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(3L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(4L, 3L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(5L, 3L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        10L,
                        20L,
                        30L,
                        null,
                        new TestFullDetailProjection(
                                30L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(10L, 30L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(20L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(30L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(40L, 30L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(50L, 30L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        100L,
                        200L,
                        300L,
                        null,
                        new TestFullDetailProjection(
                                300L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(100L, 300L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(200L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(300L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(400L, 300L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(500L, 300L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        1000L,
                        2000L,
                        3000L,
                        null,
                        new TestFullDetailProjection(
                                3000L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(1000L, 3000L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(2000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(3000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(4000L, 3000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(5000L, 3000L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                ),
                new ExaminationAnswerEntity(
                        10000L,
                        20000L,
                        30000L,
                        null,
                        new TestFullDetailProjection(
                                30000L,
                                "Question 1",
                                null,
                                "Comment 1",
                                null,
                                TestStatus.ACTIVE,
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                List.of(
                                        new TestVariantEntity(10000L, 30000L, "Title 1", null, "Explanation 1", null, true),
                                        new TestVariantEntity(20000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(30000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(40000L, 30000L, "Title 1", null, "Explanation 1", null, false),
                                        new TestVariantEntity(50000L, 30000L, "Title 1", null, "Explanation 1", null, false)
                                ),
                                Collections.emptyList(),
                                Collections.emptyMap()
                        )
                )
        );

        Mockito.when(examinationAnswerRepository.getDetailedAnswersByExaminationId(ArgumentMatchers.any())).thenReturn(answerEntities);

        Mockito.when(propertyService.getProperty(ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName())).thenReturn(Optional.of(new PropertyEntity(
                1L,
                ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName(),
                "80"
        )));

        ExaminationResultDto result = examinationAnswerService.getResult(4L);

        assertFalse(result.isSuccess());
        assertEquals(0, result.getCorrectCount());
        assertEquals(5, result.getTotal());
    }

    @Test
    void shouldReturnNotSuccessOnEmptyTestsList() {
        Mockito.when(examinationAnswerRepository.getDetailedAnswersByExaminationId(ArgumentMatchers.any())).thenReturn(Collections.emptyList());
        Mockito.when(propertyService.getProperty(ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName())).thenReturn(Optional.of(new PropertyEntity(
                1L,
                ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE.getName(),
                "80"
        )));

        ExaminationResultDto result = examinationAnswerService.getResult(4L);

        assertFalse(result.isSuccess());
        assertEquals(0, result.getCorrectCount());
        assertEquals(0, result.getTotal());
    }

    @Test
    void shouldThrowExceptionOnInvalidThresholdPropertyName() {
        Mockito.when(propertyService.getProperty(ArgumentMatchers.any())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> examinationAnswerService.getResult(3L));

        assertEquals("No such property", exception.getMessage());
    }

    private List<ExaminationTestDto> mockExaminationTests(long count) {
        return LongStream.range(0, count)
                .mapToObj(this::mockExaminationTest)
                .collect(Collectors.toList());
    }

    private ExaminationTestDto mockExaminationTest(long testId) {
        return new ExaminationTestDto(
                testId,
                "Question" + testId,
                null,
                null,
                List.of(
                        new ExaminationVariantDto(null, testId, "Title 1", null),
                        new ExaminationVariantDto(null, testId, "Title 2", null),
                        new ExaminationVariantDto(null, testId, "Title 3", null),
                        new ExaminationVariantDto(null, testId, "Title 4", null),
                        new ExaminationVariantDto(null, testId, "Title 5", null)
                )
        );
    }

    private ExaminationAnswerDto mockExaminationAnswer(Long testId, Long variantId) {
        return new ExaminationAnswerDto(
                null,
                5L,
                testId,
                variantId
        );
    }

    private Long getVariantIdByTestId(List<ExaminationAnswerDto> answers, Long testId) {
        return answers.stream()
                .filter(answer -> Objects.equals(answer.getTestId(), testId))
                .findFirst()
                .map(ExaminationAnswerDto::getVariantId)
                .orElse(null);
    }
}
