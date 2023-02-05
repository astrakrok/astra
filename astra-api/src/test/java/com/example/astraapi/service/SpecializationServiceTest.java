package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminSpecializationFilterDto;
import com.example.astraapi.dto.specialization.RequestSpecializationDto;
import com.example.astraapi.dto.specialization.SpecializationDto;
import com.example.astraapi.dto.specialization.StepSpecializationDto;
import com.example.astraapi.entity.SpecializationEntity;
import com.example.astraapi.entity.StepEntity;
import com.example.astraapi.entity.projection.StepSpecializationProjection;
import com.example.astraapi.mapper.SpecializationMapper;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import com.example.astraapi.repository.SpecializationRepository;
import com.example.astraapi.service.impl.SpecializationServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpecializationServiceTest {
    @InjectMocks
    private SpecializationServiceImpl specializationService;
    @Mock
    private SpecializationMapper specializationMapper;
    @Mock
    private SpecializationRepository specializationRepository;

    @BeforeEach
    void beforeEach() {
        TitleQualifier titleQualifier = new TitleQualifier();
        SpecializationMapper mapper = Mappers.getMapper(SpecializationMapper.class);
        ReflectionTestUtils.setField(mapper, "titleQualifier", titleQualifier);

        lenient().when(specializationMapper.toEntity(any())).thenAnswer(invocation -> {
            SpecializationDto dto = invocation.getArgument(0);
            return mapper.toEntity(dto);
        });

        lenient().when(specializationMapper.toEntity(any(), any())).thenAnswer(invocation -> {
            Long stepId = invocation.getArgument(0);
            RequestSpecializationDto dto = invocation.getArgument(1);
            return mapper.toEntity(stepId, dto);
        });

        lenient().when(specializationMapper.toDto(any(StepSpecializationProjection.class))).thenAnswer(invocation -> {
            StepSpecializationProjection projection = invocation.getArgument(0);
            return mapper.toDto(projection);
        });

        lenient().when(specializationMapper.toDto(any(SpecializationEntity.class))).thenAnswer(invocation -> {
            SpecializationEntity entity = invocation.getArgument(0);
            return mapper.toDto(entity);
        });
    }

    @Test
    void shouldSavePassedSpecializationDtoAndReturnId() {
        doAnswer(invocation -> {
            SpecializationEntity specializationEntity = invocation.getArgument(0);
            specializationEntity.setId(4L);
            return null;
        }).when(specializationRepository).save(any());

        IdDto idDto = specializationService.save(new SpecializationDto(null, 1L, "Specialization 1"));

        assertEquals(4L, idDto.getId());
    }

    @Test
    void shouldSavePassedStepIdAndSpecializationDtoAndReturnId() {
        doAnswer(invocation -> {
            SpecializationEntity specializationEntity = invocation.getArgument(0);
            specializationEntity.setId(4L);
            return null;
        }).when(specializationRepository).save(any());

        IdDto idDto = specializationService.save(1L, new RequestSpecializationDto("Specialization 1"));

        assertEquals(4L, idDto.getId());
    }

    @Test
    void shouldReturnAllSpecializations() {
        when(specializationRepository.getAllWithSteps()).thenReturn(List.of(
                new StepSpecializationProjection(1L, "Specialization 1", new StepEntity(
                        1L, "Step 1"
                )),
                new StepSpecializationProjection(2L, "Specialization 2", new StepEntity(
                        1L, "Step 1"
                )),
                new StepSpecializationProjection(3L, "Specialization 3", new StepEntity(
                        3L, "Step 3"
                )),
                new StepSpecializationProjection(4L, "Specialization 4", new StepEntity(
                        4L, "Step 4"
                ))
        ));

        List<StepSpecializationDto> specializations = specializationService.getAll();

        assertEquals(4, specializations.size());
    }

    @Test
    void shouldReturnEmptyListSpecializations() {
        when(specializationRepository.getAllWithSteps()).thenReturn(Collections.emptyList());

        List<StepSpecializationDto> specializations = specializationService.getAll();

        assertEquals(0, specializations.size());
    }

    @Test
    void shouldReturnAllSpecializationsByStepId() {
        when(specializationRepository.getAllByStepId(1L)).thenReturn(List.of(
                new SpecializationEntity(1L, 1L, "Specialization 1"),
                new SpecializationEntity(2L, 1L, "Specialization 2")
        ));

        List<SpecializationDto> specializations = specializationService.getAll(1L);

        assertEquals(2, specializations.size());
    }

    @Test
    void shouldReturnEmptyListSpecializationsByStepId() {
        when(specializationRepository.getAllByStepId(1L)).thenReturn(Collections.emptyList());

        List<SpecializationDto> specializations = specializationService.getAll(1L);

        assertEquals(0, specializations.size());
    }

    @Test
    void shouldReturnAvailableSpecializationForExam() {
        when(specializationRepository.getNotSelectedByExamId(5L)).thenReturn(List.of(
                new StepSpecializationProjection(1L, "Specialization 1", new StepEntity(
                        1L, "Step 1"
                )),
                new StepSpecializationProjection(2L, "Specialization 2", new StepEntity(
                        1L, "Step 1"
                )),
                new StepSpecializationProjection(3L, "Specialization 3", new StepEntity(
                        3L, "Step 3"
                )),
                new StepSpecializationProjection(4L, "Specialization 4", new StepEntity(
                        4L, "Step 4"
                ))
        ));

        List<StepSpecializationDto> specializations = specializationService.getNotSelectedForExam(5L);

        assertEquals(4, specializations.size());
    }

    @Test
    void shouldReturnEmptyListOfAvailableSpecializations() {
        when(specializationRepository.getNotSelectedByExamId(5L)).thenReturn(Collections.emptyList());

        List<StepSpecializationDto> specializations = specializationService.getNotSelectedForExam(5L);

        assertEquals(0, specializations.size());
    }

    @Test
    void shouldSearchSpecializations() {
        when(specializationRepository.search(any(), any())).thenReturn(List.of(
                new SpecializationEntity(1L, 1L, "specialization 1"),
                new SpecializationEntity(2L, 1L, "specialization 2"),
                new SpecializationEntity(3L, 1L, "specialization 3")
        ));

        List<SpecializationDto> items = specializationService.search(new AdminSpecializationFilterDto(1L, 1L));

        assertEquals(3, items.size());
    }
}
