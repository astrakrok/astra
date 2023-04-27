package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.test.RequestTestingDetailTestDto;
import com.example.astraapi.dto.test.RequestTestingTestDto;
import com.example.astraapi.dto.testing.TestingDetailDto;
import com.example.astraapi.service.TestingTestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
public class AdminTestingTestControllerTest {
    @InjectMocks
    private AdminTestingTestController adminTestingTestController;
    @Mock
    private TestingTestService testingTestService;

    @Test
    void shouldReturnCorrectResponseWhenSaveWithRequestTestingTestDto() {
        when(testingTestService.save(ArgumentMatchers.<RequestTestingTestDto>any())).thenReturn(new IdDto(3L));

        ResponseEntity<IdDto> response = adminTestingTestController.save(new RequestTestingTestDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3L, response.getBody().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenSaveWithRequestTestingDetailTestDto() {
        when(testingTestService.save(ArgumentMatchers.<RequestTestingDetailTestDto>any())).thenReturn(new IdDto(3L));

        ResponseEntity<IdDto> response = adminTestingTestController.save(new RequestTestingDetailTestDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3L, response.getBody().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenDeleteTestWithRequestTestingTestDto() {
        ResponseEntity<Void> response = adminTestingTestController.deleteTest(new RequestTestingTestDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldReturnCorrectResponseWhenDeleteTestWithId() {
        ResponseEntity<Void> response = adminTestingTestController.deleteTest(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldReturnCorrectResponseWhenGetTestings() {
        when(testingTestService.getTestings(any())).thenReturn(List.of(new TestingDetailDto(), new TestingDetailDto(), new TestingDetailDto()));

        ResponseEntity<List<TestingDetailDto>> response = adminTestingTestController.getTestings(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }
}
