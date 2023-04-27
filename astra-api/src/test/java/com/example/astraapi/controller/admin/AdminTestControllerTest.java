package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.filter.AdminTestFilterDto;
import com.example.astraapi.dto.test.RequestTestDto;
import com.example.astraapi.dto.test.TestFullDetailDto;
import com.example.astraapi.dto.test.TestShortDetailDto;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.service.TestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminTestControllerTest {
    @InjectMocks
    private AdminTestController adminTestController;
    @Mock
    private TestService testService;

    @Test
    void shouldReturnCorrectResponseWhenSave() {
        when(testService.save(any())).thenReturn(new TestFullDetailDto(1L, null, null, null, null, null, null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()));

        ResponseEntity<TestFullDetailDto> response = adminTestController.save(new RequestTestDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenSaveDraft() {
        when(testService.saveDraft(any())).thenReturn(new TestFullDetailDto(1L, null, null, null, null, null, null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()));

        ResponseEntity<TestFullDetailDto> response = adminTestController.saveDraft(new RequestTestDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenUpdate() {
        when(testService.update(any(), any())).thenReturn(Optional.of(new TestFullDetailDto(1L, null, null, null, null, null, null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap())));

        ResponseEntity<Optional<TestFullDetailDto>> response = adminTestController.update(2L, new RequestTestDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isPresent());
        assertEquals(1L, response.getBody().get().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenUpdateDraft() {
        when(testService.updateDraft(any(), any())).thenReturn(Optional.of(new TestFullDetailDto(1L, null, null, null, null, null, null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap())));

        ResponseEntity<Optional<TestFullDetailDto>> response = adminTestController.updateDraft(2L, new RequestTestDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isPresent());
        assertEquals(1L, response.getBody().get().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenGetAll() {
        when(testService.getAll(any(), any())).thenReturn(new Page<>(List.of(new TestShortDetailDto()),1,1));

        ResponseEntity<Page<TestShortDetailDto>> response = adminTestController.getAll(new AdminTestFilterDto(), new Pageable(1, 0));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getItems().size());
        assertEquals(1, response.getBody().getRows());
        assertEquals(1, response.getBody().getPageSize());
        assertEquals(1, response.getBody().getPagesCount());
    }

    @Test
    void shouldReturnCorrectResponseWhenGetDetailedTest() {
        when(testService.getDetailedTest(any())).thenReturn(Optional.of(new TestFullDetailDto(1L, null, null, null, null, null, null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap())));

        ResponseEntity<Optional<TestFullDetailDto>> response = adminTestController.getDetailedTest(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isPresent());
        assertEquals(1L, response.getBody().get().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenDelete() {
        ResponseEntity<Void> response = adminTestController.delete(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
}
