package com.example.astraapi.controller;

import com.example.astraapi.dto.specialization.SpecializationDto;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StepSpecializationControllerTest {
    @InjectMocks
    private StepSpecializationController stepSpecializationController;
    @Mock
    private SpecializationService specializationService;

    @Test
    void shouldReturnCorrectResponseWhenGetByStepId() {
        when(specializationService.getAll(any())).thenReturn(List.of(new SpecializationDto(1L, 2L, "title")));

        ResponseEntity<List<SpecializationDto>> response = stepSpecializationController.getByStepId(3L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals(2L, response.getBody().get(0).getStepId());
        assertEquals("title", response.getBody().get(0).getTitle());
    }
}
