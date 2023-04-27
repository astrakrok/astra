package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminSubjectFilterDto;
import com.example.astraapi.dto.subject.RequestSubjectDto;
import com.example.astraapi.dto.subject.ResponseSubjectDto;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.service.SubjectService;
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
public class AdminSubjectControllerTest {
    @InjectMocks
    private AdminSubjectController adminSubjectController;
    @Mock
    private SubjectService subjectService;

    @Test
    void shouldReturnCorrectResponseWhenSave() {
        when(subjectService.save(any())).thenReturn(new IdDto(1L));

        ResponseEntity<IdDto> response = adminSubjectController.save(new RequestSubjectDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenSearch() {
        when(subjectService.search(any(), any())).thenReturn(new Page<>(List.of(new ResponseSubjectDto()), 1, 1));

        ResponseEntity<Page<ResponseSubjectDto>> response = adminSubjectController.search(new AdminSubjectFilterDto(), new Pageable(1, 0));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getItems().size());
        assertEquals(1, response.getBody().getRows());
        assertEquals(1, response.getBody().getPageSize());
        assertEquals(1, response.getBody().getPagesCount());
    }

    @Test
    void shouldReturnCorrectResponseWhenUpdate() {
        ResponseEntity<Void> response = adminSubjectController.updateSubject(2L, new RequestSubjectDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
}
