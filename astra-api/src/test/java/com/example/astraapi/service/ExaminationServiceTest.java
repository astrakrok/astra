package com.example.astraapi.service;

import com.example.astraapi.TestUtils;
import com.example.astraapi.config.ExaminationProperties;
import com.example.astraapi.dto.UserDto;
import com.example.astraapi.dto.examination.ExaminationAnswerDto;
import com.example.astraapi.dto.examination.ExaminationSearchDto;
import com.example.astraapi.dto.examination.ExaminationStateDto;
import com.example.astraapi.dto.examination.ExaminationVariantDto;
import com.example.astraapi.dto.test.ExaminationTestDto;
import com.example.astraapi.entity.ExaminationAnswerEntity;
import com.example.astraapi.entity.ExaminationEntity;
import com.example.astraapi.mapper.ExaminationAnswerMapper;
import com.example.astraapi.mapper.ExaminationMapper;
import com.example.astraapi.repository.ExaminationRepository;
import com.example.astraapi.service.impl.ExaminationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ExaminationServiceTest {
    @InjectMocks
    private ExaminationServiceImpl examinationService;
    @Mock
    private ExaminationProperties examinationProperties;
    @Mock
    private ExaminationRepository examinationRepository;
    @Spy
    private ExaminationMapper examinationMapper;
    @Mock
    private ExaminationAnswerService examinationAnswerService;
    @Mock
    private TimeZoneService timeZoneService;
    @Mock
    private AuthContext authContext;

    @BeforeEach
    void beforeEach() {
        ExaminationMapper mapStructExaminationMapper = Mappers.getMapper(ExaminationMapper.class);
        ExaminationAnswerMapper mapStructExaminationAnswerMapper = Mappers.getMapper(ExaminationAnswerMapper.class);
        ReflectionTestUtils.setField(mapStructExaminationMapper, "examinationAnswerMapper", mapStructExaminationAnswerMapper);

        Mockito.lenient().when(examinationMapper.toDto(ArgumentMatchers.any())).thenAnswer(invocation -> {
            ExaminationEntity entity = invocation.getArgument(0);
            return mapStructExaminationMapper.toDto(entity);
        });

        Mockito.lenient().when(examinationMapper.toEntity(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenAnswer(invocation -> {
            Long userId = invocation.getArgument(0);
            Long testingId = invocation.getArgument(1);
            LocalDateTime finishedAt = invocation.getArgument(2);
            return mapStructExaminationMapper.toEntity(userId, testingId, finishedAt);
        });

        Mockito.lenient().when(examinationProperties.getDurationInMinutes()).thenReturn(10);
        Mockito.lenient().when(examinationProperties.getFinishedAtDeviationSeconds()).thenReturn(0);
        Mockito.lenient().when(examinationProperties.getThresholdPercentage()).thenReturn(50);
    }

    @Test
    void shouldCreateNewExaminationOnStart() {
        Long examinationId = 4L;
        ExaminationEntity[] createdExamination = new ExaminationEntity[1];

        Mockito.when(examinationRepository.findExaminationWithAnswers(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(Optional.empty());
        Mockito.doAnswer(invocation -> {
            ExaminationEntity entity = invocation.getArgument(0);
            entity.setId(examinationId);
            createdExamination[0] = entity;
            return null;
        }).when(examinationRepository).save(ArgumentMatchers.any());

        Mockito.when(authContext.getUser()).thenReturn(new UserDto(1L, null, null, null, null, null, null, Set.of()));

        Mockito.when(timeZoneService.toUtc(ArgumentMatchers.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Mockito.when(examinationAnswerService.createTestsForExamination(
                        ArgumentMatchers.eq(4L),
                        ArgumentMatchers.any()))
                .thenReturn(mockTests(5));

        ExaminationStateDto examinationState = examinationService.start(new ExaminationSearchDto(6L));

        assertEquals(5, examinationState.getTests().size());
        assertEquals(examinationId, examinationState.getId());
        assertEquals(examinationId, createdExamination[0].getId());
        assertEquals(6L, createdExamination[0].getTestingId());
    }

    @Test
    void shouldStartExistingExamination() {
        Long examinationId = 4L;

        Mockito.when(examinationRepository.findExaminationWithAnswers(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(Optional.of(mockExaminationEntity(5)));

        Mockito.when(authContext.getUser()).thenReturn(new UserDto(1L, null, null, null, null, null, null, Set.of()));

        Mockito.when(examinationAnswerService.getExaminationTests(ArgumentMatchers.any())).thenAnswer(invocation -> {
            List<ExaminationAnswerDto> answers = invocation.getArgument(0);
            return mockTests(answers.size());
        });

        ExaminationStateDto examinationState = examinationService.start(new ExaminationSearchDto(6L));

        assertEquals(5, examinationState.getTests().size());
        assertEquals(examinationId, examinationState.getId());
    }

    @Test
    void shouldUpdateExaminationAnswer() {
        ExaminationAnswerDto[] examinationAnswerDto = new ExaminationAnswerDto[1];

        Mockito.when(authContext.getUser()).thenReturn(new UserDto(1L, null, null, null, null, null, null, Set.of()));

        Mockito.when(examinationRepository.exists(
                        ArgumentMatchers.eq(4L),
                        ArgumentMatchers.eq(1L),
                        ArgumentMatchers.any()))
                .thenReturn(true);

        Mockito.doAnswer(invocation -> {
            ExaminationAnswerDto dto = invocation.getArgument(0);
            examinationAnswerDto[0] = dto;
            return null;
        }).when(examinationAnswerService).updateAnswer(ArgumentMatchers.any());

        examinationService.updateAnswer(4L, new ExaminationAnswerDto(null, null, 5L, 10L));

        assertEquals(4L, examinationAnswerDto[0].getExaminationId());
    }

    @Test
    void shouldThrowExceptionOnNonExistingExamination() {
        Mockito.when(authContext.getUser()).thenReturn(new UserDto(1L, null, null, null, null, null, null, Set.of()));

        Mockito.when(examinationRepository.exists(
                        ArgumentMatchers.eq(4L),
                        ArgumentMatchers.eq(1L),
                        ArgumentMatchers.any()))
                .thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> examinationService.updateAnswer(4L, new ExaminationAnswerDto(null, null, 5L, 10L)));
        assertEquals("You cannot modify answers for expired or non-existing examination", exception.getMessage());
    }

    @Test
    void shouldFinishExamination() {
        LocalDateTime[] finishedAt = new LocalDateTime[1];

        LocalDateTime dateTime = LocalDateTime.now();

        Mockito.when(authContext.getUser()).thenReturn(new UserDto(1L, null, null, null, null, null, null, Set.of()));

        Mockito.when(examinationRepository.exists(
                        ArgumentMatchers.eq(4L),
                        ArgumentMatchers.eq(1L),
                        ArgumentMatchers.any()))
                .thenReturn(true);

        Mockito.when(timeZoneService.toUtc(ArgumentMatchers.any())).thenReturn(dateTime);

        Mockito.doAnswer(invocation -> {
                    LocalDateTime ldt = invocation.getArgument(1);
                    finishedAt[0] = ldt;
                    return null;
                }).when(examinationRepository)
                .updateFinishedAtById(ArgumentMatchers.eq(4L), ArgumentMatchers.any());

        examinationService.finish(4L);

        assertEquals(dateTime, finishedAt[0]);
    }

    private List<ExaminationTestDto> mockTests(int count) {
        return IntStream.range(0, count)
                .mapToObj(this::mockTest)
                .collect(Collectors.toList());
    }

    private ExaminationTestDto mockTest(int id) {
        return new ExaminationTestDto(
                (long) id,
                "Test question",
                null,
                null,
                IntStream.range(0, 5)
                        .mapToObj(index -> mockTestVariant(id, index))
                        .collect(Collectors.toList())
        );
    }

    private ExaminationVariantDto mockTestVariant(int testId, int index) {
        return new ExaminationVariantDto(
                (long) testId * 100 + index,
                (long) testId,
                "Title" + index,
                null
        );
    }

    private ExaminationEntity mockExaminationEntity(int count) {
        return new ExaminationEntity(
                4L,
                1L,
                2L,
                LocalDateTime.now().plusMinutes(100),
                IntStream.range(0, count)
                        .mapToObj(index -> new ExaminationAnswerEntity(
                                (long) index * 2,
                                4L,
                                (long) index,
                                (long) TestUtils.nextInt(index * 100, index * 100 + 4),
                                null))
                        .collect(Collectors.toList())
        );
    }
}
