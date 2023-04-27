package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.specialization.RequestSpecializationDto;
import com.example.astraapi.service.SpecializationService;
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
public class AdminStepSpecializationControllerTest {
    @InjectMocks
    private AdminStepSpecializationController adminStepSpecializationController;
    @Mock
    private SpecializationService specializationService;

    @Test
    void shouldReturnCorrectResponseWhenSave() {
        when(specializationService.save(any(), any())).thenReturn(new IdDto(7L));

        ResponseEntity<IdDto> response = adminStepSpecializationController.save(3L, new RequestSpecializationDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(7L, response.getBody().getId());
    }
}
