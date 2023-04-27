package com.example.astraapi.controller;

import com.example.astraapi.dto.examination.ExaminationAnswerDto;
import com.example.astraapi.dto.examination.ExaminationResultDto;
import com.example.astraapi.dto.examination.ExaminationSearchDto;
import com.example.astraapi.dto.examination.ExaminationStateDto;
import com.example.astraapi.dto.test.ExaminationTestDto;
import com.example.astraapi.service.ExaminationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminationControllerTest {
    @InjectMocks
    private ExaminationController examinationController;
    @Mock
    private ExaminationService examinationService;

    @Test
    void shouldReturnCorrectResponseWhenStartExamination() {
        when(examinationService.start(any())).thenReturn(new ExaminationStateDto(
                3L,
                List.of(
                        new ExaminationTestDto(
                                1L,
                                "question",
                                null,
                                null,
                                Collections.emptyList())),
                LocalDateTime.of(2000, 12, 12, 10, 10)));

        ResponseEntity<ExaminationStateDto> response = examinationController.start(new ExaminationSearchDto(4L));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3L, response.getBody().getId());
        assertEquals(1, response.getBody().getTests().size());
        assertEquals(LocalDateTime.of(2000, 12, 12, 10, 10), response.getBody().getFinishedAt());
    }

    @Test
    void shouldReturnCorrectResponseWhenUpdateAnswer() {
        ResponseEntity<Void> response = examinationController.updateAnswer(3L, new ExaminationAnswerDto(1L, 2L, 3L, 4L));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldReturnCorrectResponseWhenFinishTest() {
        when(examinationService.finish(any())).thenReturn(new ExaminationResultDto(Collections.emptyList(), 5L, 6L, true));

        ResponseEntity<ExaminationResultDto> response = examinationController.finishTest(6L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getTests().size());
        assertEquals(5L, response.getBody().getCorrectCount());
        assertEquals(6L, response.getBody().getTotal());
        assertTrue(response.getBody().isSuccess());
    }
}
