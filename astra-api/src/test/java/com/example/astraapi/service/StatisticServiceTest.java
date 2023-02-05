package com.example.astraapi.service;

import com.example.astraapi.dto.UserDto;
import com.example.astraapi.dto.examination.ExaminationStatisticDto;
import com.example.astraapi.dto.filter.StepsStatisticFilterDto;
import com.example.astraapi.dto.statistic.StepStatisticDto;
import com.example.astraapi.entity.ExaminationStatisticInfoEntity;
import com.example.astraapi.entity.projection.StepStatisticProjection;
import com.example.astraapi.mapper.ExaminationStatisticMapper;
import com.example.astraapi.mapper.SubjectMapper;
import com.example.astraapi.mapper.TestingMapper;
import com.example.astraapi.repository.ExaminationStatisticRepository;
import com.example.astraapi.service.impl.StatisticServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticServiceTest {
    @InjectMocks
    private StatisticServiceImpl statisticService;
    @Mock
    private ExaminationStatisticMapper examinationStatisticMapper;
    @Mock
    private ExaminationStatisticRepository examinationStatisticRepository;
    @Mock
    private AuthContext authContext;

    @BeforeEach
    void beforeEach() {
        TestingMapper testingMapper = Mappers.getMapper(TestingMapper.class);
        SubjectMapper subjectMapper = Mappers.getMapper(SubjectMapper.class);
        ExaminationStatisticMapper mapper = Mappers.getMapper(ExaminationStatisticMapper.class);
        ReflectionTestUtils.setField(mapper, "testingMapper", testingMapper);
        ReflectionTestUtils.setField(mapper, "subjectMapper", subjectMapper);

        lenient().when(examinationStatisticMapper.toDto(any(ExaminationStatisticInfoEntity.class))).thenAnswer(invocation -> mapper.toDto(invocation.<ExaminationStatisticInfoEntity>getArgument(0)));

        lenient().when(examinationStatisticMapper.toDto(any(StepStatisticProjection.class))).thenAnswer(invocation -> mapper.toDto(invocation.<StepStatisticProjection>getArgument(0)));
    }

    @Test
    void shouldReturnExaminationStatistic() {
        when(authContext.getUser()).thenReturn(new UserDto());
        when(examinationStatisticRepository.getAllWithTestingByUserId(any())).thenReturn(List.of(
                new ExaminationStatisticInfoEntity(
                        10L,
                        10L,
                        10L,
                        10L,
                        null
                ),
                new ExaminationStatisticInfoEntity(
                        1L,
                        1L,
                        1L,
                        1L,
                        null
                )
        ));

        List<ExaminationStatisticDto> statistics = statisticService.getStatistics();

        assertEquals(2L, statistics.size());
    }

    @Test
    void shouldReturnStepsStatistic() {
        when(authContext.getUser()).thenReturn(new UserDto());
        when(examinationStatisticRepository.getStepsStatisticByUserId(any(), any())).thenReturn(List.of(
                new StepStatisticProjection(null, "title 1", Collections.emptyList()),
                new StepStatisticProjection(null, "title 2", Collections.emptyList()),
                new StepStatisticProjection(null, "title 3", Collections.emptyList())
        ));

        List<StepStatisticDto> stepsStatistic = statisticService.getStepsStatistic(new StepsStatisticFilterDto(6L));

        assertEquals(3L, stepsStatistic.size());
    }
}
