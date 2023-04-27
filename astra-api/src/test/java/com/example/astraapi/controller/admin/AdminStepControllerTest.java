package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.step.StepDto;
import com.example.astraapi.service.StepService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminStepControllerTest {
    @InjectMocks
    private AdminStepController adminStepController;
    @Mock
    private StepService stepService;

    @Test
    void shouldReturnCorrectResponseWhenSave() {
        when(stepService.save(any())).thenReturn(new IdDto(8L));

        ResponseEntity<IdDto> response = adminStepController.save(new StepDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(8L, response.getBody().getId());
    }
}
