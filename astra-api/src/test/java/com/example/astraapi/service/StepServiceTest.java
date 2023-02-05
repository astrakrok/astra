package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.step.StepDto;
import com.example.astraapi.entity.StepEntity;
import com.example.astraapi.mapper.StepMapper;
import com.example.astraapi.repository.StepRepository;
import com.example.astraapi.service.impl.StepServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StepServiceTest {
    @InjectMocks
    private StepServiceImpl stepService;
    @Mock
    private StepMapper stepMapper;
    @Mock
    private StepRepository stepRepository;

    @Test
    void shouldSaveStepEntityAndReturnId() {
        Mockito
                .when(stepMapper.toEntity(ArgumentMatchers.any()))
                .thenReturn(new StepEntity(null, "STEP 1"));
        Mockito
                .doAnswer(invocation -> {
                    StepEntity entity = invocation.getArgument(0);
                    entity.setId(1L);
                    return null;
                })
                .when(stepRepository)
                .save(ArgumentMatchers.any());
        IdDto idDto = stepService.save(new StepDto(1L, "STEP 1"));
        assertEquals(1L, idDto.getId());
    }

    @Test
    void shouldReturnAllSteps() {
        Mockito
                .when(stepRepository.getAll())
                .thenReturn(List.of(
                        new StepEntity(1L, "STEP 1"),
                        new StepEntity(2L, "STEP 2"),
                        new StepEntity(3L, "STEP 3")));
        Mockito
                .when(stepMapper.toDto(ArgumentMatchers.any()))
                .then(invocation -> {
                    StepEntity entity = invocation.getArgument(0);
                    return new StepDto(entity.getId(), entity.getTitle());
                });
        List<StepDto> steps = stepService.getAll();
        assertEquals(3, steps.size());
        assertEquals("STEP 1", steps.get(0).getTitle());
        assertEquals("STEP 2", steps.get(1).getTitle());
        assertEquals("STEP 3", steps.get(2).getTitle());
    }
}
