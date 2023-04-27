package com.example.astraapi.controller;

import com.example.astraapi.dto.specialization.StepSpecializationDto;
import com.example.astraapi.dto.step.StepDto;
import com.example.astraapi.service.SpecializationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpecializationControllerTest {
    @InjectMocks
    private SpecializationController specializationController;
    @Mock
    private SpecializationService specializationService;

    @Test
    void shouldReturnCorrectResponseWhenGetAll() {
        when(specializationService.getAll()).thenReturn(List.of(new StepSpecializationDto(1L, "specialization title", new StepDto(1L, "step title"))));

        ResponseEntity<List<StepSpecializationDto>> response = specializationController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals("specialization title", response.getBody().get(0).getTitle());
        assertEquals(1L, response.getBody().get(0).getStep().getId());
        assertEquals("step title", response.getBody().get(0).getStep().getTitle());
    }
}
