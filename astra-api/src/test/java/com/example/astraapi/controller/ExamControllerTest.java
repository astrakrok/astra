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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExamControllerTest {
    @InjectMocks
    private ExamController examController;
    @Mock
    private ExamService examService;

    @Test
    void shouldReturnCorrectResponseWhenGetAll() {
        when(examService.getAll()).thenReturn(List.of(new ResponseExamDto(5L, "title")));

        ResponseEntity<List<ResponseExamDto>> response = examController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("title", response.getBody().get(0).getTitle());
    }
}
