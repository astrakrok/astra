package com.example.astraapi.controller;

import com.example.astraapi.dto.testing.TestingDescriptionDto;
import com.example.astraapi.dto.testing.TestingDetailDto;
import com.example.astraapi.dto.testing.TestingDto;
import com.example.astraapi.service.TestingService;
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
public class TestingControllerTest {
    @InjectMocks
    private TestingController testingController;
    @Mock
    private TestingService testingService;

    @Test
    void shouldReturnCorrectResponseWhenGetDescription() {
        when(testingService.getDescription()).thenReturn(new TestingDescriptionDto("training description", "examination description", "adaptive description"));

        ResponseEntity<TestingDescriptionDto> response = testingController.getDescription();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("training description", response.getBody().getTrainingDescription());
        assertEquals("examination description", response.getBody().getExaminationDescription());
        assertEquals("adaptive description", response.getBody().getAdaptiveDescription());
    }

    @Test
    void shouldReturnCorrectResponseWhenGetAvailableTestings() {
        when(testingService.getAvailableTestings()).thenReturn(List.of(new TestingDetailDto(), new TestingDetailDto(), new TestingDetailDto()));

        ResponseEntity<List<TestingDetailDto>> response = testingController.getAvailableTestings();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    @Test
    void shouldReturnCorrectResponseWhenGetOne() {
        when(testingService.getOne(any(), any())).thenReturn(new TestingDto(1L, 2L, 3L));

        ResponseEntity<TestingDto> response = testingController.getOne(6L, 8L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals(2L, response.getBody().getExamId());
        assertEquals(3L, response.getBody().getSpecializationId());
    }
}
