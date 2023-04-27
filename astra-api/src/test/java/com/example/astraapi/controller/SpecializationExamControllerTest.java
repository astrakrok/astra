package com.example.astraapi.controller;

import com.example.astraapi.dto.exam.ResponseExamDto;
import com.example.astraapi.service.ExamService;
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
public class SpecializationExamControllerTest {
    @InjectMocks
    private SpecializationExamController specializationExamController;
    @Mock
    private ExamService examService;

    @Test
    void shouldReturnCorrectResponseWhenGetActive() {
        when(examService.getActive(any())).thenReturn(List.of(new ResponseExamDto(1L, "title 1"), new ResponseExamDto(2L, "title 2")));

        ResponseEntity<List<ResponseExamDto>> response = specializationExamController.getActive(6L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals("title 1", response.getBody().get(0).getTitle());
        assertEquals(2L, response.getBody().get(1).getId());
        assertEquals("title 2", response.getBody().get(1).getTitle());
    }
}
