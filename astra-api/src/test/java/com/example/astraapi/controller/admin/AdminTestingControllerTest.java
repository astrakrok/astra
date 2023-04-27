package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.exam.ResponseExamDto;
import com.example.astraapi.dto.filter.AdminAvailableTestingTestsFilterDto;
import com.example.astraapi.dto.filter.AdminTestingTestsFilterDto;
import com.example.astraapi.dto.specialization.SpecializationDto;
import com.example.astraapi.dto.test.TestingShortTestDto;
import com.example.astraapi.dto.test.TestingTestQuestionDto;
import com.example.astraapi.dto.testing.RequestTestingDto;
import com.example.astraapi.dto.testing.TestingInfoDto;
import com.example.astraapi.dto.testing.TestingStatusDto;
import com.example.astraapi.dto.testing.TestingWithSpecializationDto;
import com.example.astraapi.meta.TestingStatus;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.model.TestingPage;
import com.example.astraapi.service.TestingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminTestingControllerTest {
    @InjectMocks
    private AdminTestingController adminTestingController;
    @Mock
    private TestingService testingService;

    @Test
    void shouldReturnCorrectResponseWhenSave() {
        when(testingService.save(any())).thenReturn(new IdDto(2L));

        ResponseEntity<IdDto> response = adminTestingController.save(new RequestTestingDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2L, response.getBody().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenGetWithSpecializations() {
        when(testingService.getWithSpecializations(any())).thenReturn(List.of(new TestingWithSpecializationDto(), new TestingWithSpecializationDto()));

        ResponseEntity<List<TestingWithSpecializationDto>> response = adminTestingController.getWithSpecializations(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void shouldReturnCorrectResponseWhenGetTestingInfo() {
        when(testingService.getTestingInfo(any())).thenReturn(
                Optional.of(
                        new TestingInfoDto(
                                1L,
                                TestingStatus.ACTIVE,
                                100L,
                                new ResponseExamDto(10L, "exam title"),
                                new SpecializationDto(100L, 1000L, "specialization title"))));

        ResponseEntity<Optional<TestingInfoDto>> response = adminTestingController.getTestingInfo(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isPresent());
        assertEquals(1L, response.getBody().get().getId());
        assertEquals(TestingStatus.ACTIVE, response.getBody().get().getStatus());
        assertEquals(100L, response.getBody().get().getTestsCount());
        assertEquals(10L, response.getBody().get().getExam().getId());
        assertEquals("exam title", response.getBody().get().getExam().getTitle());
        assertEquals(100L, response.getBody().get().getSpecialization().getId());
        assertEquals(1000L, response.getBody().get().getSpecialization().getStepId());
        assertEquals("specialization title", response.getBody().get().getSpecialization().getTitle());
    }

    @Test
    void shouldReturnCorrectResponseWhenGetTestsQuestions() {
        when(testingService.getTestsQuestions(any(), any(), any())).thenReturn(new TestingPage<>(List.of(new TestingTestQuestionDto()), 1L, 1L, 2L));

        ResponseEntity<TestingPage<TestingTestQuestionDto>> response = adminTestingController.getTestsQuestions(1L, new AdminTestingTestsFilterDto(), new Pageable(1, 0));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getItems().size());
        assertEquals(1L, response.getBody().getRows());
        assertEquals(1L, response.getBody().getPageSize());
        assertEquals(2L, response.getBody().getTestingId());
        assertEquals(1L, response.getBody().getPagesCount());
    }

    @Test
    void shouldReturnCorrectResponseWhenGetAvailableTestingTests() {
        when(testingService.getNotSelectedTestingTests(any(), any(), any())).thenReturn(new Page<>(List.of(new TestingShortTestDto()), 1L, 1L));

        ResponseEntity<Page<TestingShortTestDto>> response = adminTestingController.getAvailableTestingTests(1L, new AdminAvailableTestingTestsFilterDto(), new Pageable(1, 0));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getItems().size());
        assertEquals(1L, response.getBody().getRows());
        assertEquals(1L, response.getBody().getPageSize());
        assertEquals(1L, response.getBody().getPagesCount());
    }

    @Test
    void shouldReturnCorrectResponseWhenUpdateStatus() {
        when(testingService.updateStatus(any(), any())).thenReturn(Optional.of(
                new TestingInfoDto(
                        1L,
                        TestingStatus.ACTIVE,
                        100L,
                        new ResponseExamDto(10L, "exam title"),
                        new SpecializationDto(100L, 1000L, "specialization title"))));

        ResponseEntity<Optional<TestingInfoDto>> response = adminTestingController.updateStatus(2L, new TestingStatusDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isPresent());
        assertEquals(1L, response.getBody().get().getId());
        assertEquals(TestingStatus.ACTIVE, response.getBody().get().getStatus());
        assertEquals(100L, response.getBody().get().getTestsCount());
        assertEquals(10L, response.getBody().get().getExam().getId());
        assertEquals("exam title", response.getBody().get().getExam().getTitle());
        assertEquals(100L, response.getBody().get().getSpecialization().getId());
        assertEquals(1000L, response.getBody().get().getSpecialization().getStepId());
        assertEquals("specialization title", response.getBody().get().getSpecialization().getTitle());
    }
}
