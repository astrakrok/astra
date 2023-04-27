package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminSpecializationFilterDto;
import com.example.astraapi.dto.specialization.SpecializationDto;
import com.example.astraapi.service.SpecializationService;
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
public class AdminSpecializationControllerTest {
    @InjectMocks
    private AdminSpecializationController adminSpecializationController;
    @Mock
    private SpecializationService specializationService;

    @Test
    void shouldReturnCorrectResponseWhenSave() {
        when(specializationService.save(any())).thenReturn(new IdDto(10L));

        ResponseEntity<IdDto> response = adminSpecializationController.save(new SpecializationDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(10L, response.getBody().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenSearch() {
        when(specializationService.search(any())).thenReturn(List.of(new SpecializationDto(), new SpecializationDto(), new SpecializationDto()));

        ResponseEntity<List<SpecializationDto>> response = adminSpecializationController.search(new AdminSpecializationFilterDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }
}
