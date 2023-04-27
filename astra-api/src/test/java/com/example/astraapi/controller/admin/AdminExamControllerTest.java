package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.exam.RequestExamDto;
import com.example.astraapi.dto.specialization.StepSpecializationDto;
import com.example.astraapi.service.ExamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminExamControllerTest {
    @InjectMocks
    private AdminExamController adminExamController;
    @Mock
    private ExamService examService;

    @Test
    void shouldReturnCorrectResponseWhenSave() {
        when(examService.save(any())).thenReturn(new IdDto(5L));

        ResponseEntity<IdDto> response = adminExamController.save(new RequestExamDto("exam title"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(5L, response.getBody().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenDelete() {
        ResponseEntity<Void> response = adminExamController.delete(3L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldReturnCorrectResponseWhenUpdate() {
        ResponseEntity<Void> response = adminExamController.update(3L, new RequestExamDto("update title"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldReturnCorrectResponseWhenGetAvailableSpecializations() {
        when(examService.getAvailableSpecializations(any())).thenReturn(List.of(new StepSpecializationDto(), new StepSpecializationDto()));

        ResponseEntity<List<StepSpecializationDto>> response = adminExamController.getAvailableSpecializations(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}
