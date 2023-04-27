package com.example.astraapi.controller;

import com.example.astraapi.dto.TrainingSearchDto;
import com.example.astraapi.dto.subject.ResponseSubjectDto;
import com.example.astraapi.dto.test.AdaptiveTestDto;
import com.example.astraapi.dto.test.TrainingTestDto;
import com.example.astraapi.dto.test.variant.TrainingVariantDto;
import com.example.astraapi.dto.testing.TestingDetailDto;
import com.example.astraapi.service.TestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestControllerTest {
    @InjectMocks
    private TestController testController;
    @Mock
    private TestService testService;

    @Test
    void shouldReturnCorrectResponseWhenGetTrainingTesting() {
        when(testService.getTrainingTests(any())).thenReturn(
                List.of(
                        new TrainingTestDto(
                                1L,
                                "question",
                                null,
                                "comment",
                                null,
                                List.of(
                                        new TrainingVariantDto(),
                                        new TrainingVariantDto(),
                                        new TrainingVariantDto()))));

        ResponseEntity<List<TrainingTestDto>> response = testController.getTrainingTesting(new TrainingSearchDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals("question", response.getBody().get(0).getQuestion());
        assertNull(response.getBody().get(0).getQuestionSvg());
        assertEquals("comment", response.getBody().get(0).getComment());
        assertNull(response.getBody().get(0).getCommentSvg());
        assertEquals(3, response.getBody().get(0).getVariants().size());
    }

    @Test
    void shouldReturnCorrectResponseWhenGetAdaptiveTesting() {
        when(testService.getAdaptiveTests(anyLong())).thenReturn(
                List.of(
                        new AdaptiveTestDto(
                                1L,
                                "question",
                                null,
                                "comment",
                                null,
                                List.of(new TestingDetailDto(), new TestingDetailDto()),
                                List.of(new ResponseSubjectDto(), new ResponseSubjectDto(), new ResponseSubjectDto()),
                                List.of(new TrainingVariantDto()))));

        ResponseEntity<List<AdaptiveTestDto>> response = testController.getAdaptiveTesting(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals("question", response.getBody().get(0).getQuestion());
        assertNull(response.getBody().get(0).getQuestionSvg());
        assertEquals("comment", response.getBody().get(0).getComment());
        assertNull(response.getBody().get(0).getCommentSvg());
        assertEquals(2, response.getBody().get(0).getTestings().size());
        assertEquals(3, response.getBody().get(0).getSubjects().size());
        assertEquals(1, response.getBody().get(0).getVariants().size());
    }
}
