package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.dto.filter.AdminImportTestFilterDto;
import com.example.astraapi.dto.importing.FileImportDto;
import com.example.astraapi.dto.importing.ImportStatsDto;
import com.example.astraapi.dto.importing.WebImportDto;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.service.TransferService;
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
public class AdminTransferControllerTest {
    @InjectMocks
    private AdminTransferController adminTransferController;
    @Mock
    private TransferService transferService;

    @Test
    void shouldReturnCorrectResponseWhenImportFromFile() {
        when(transferService.importFromFile(any())).thenReturn(new IdDto(5L));

        ResponseEntity<IdDto> response = adminTransferController.importFromFile(new FileImportDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(5L, response.getBody().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenImportFromWeb() {
        when(transferService.importFromWeb(any())).thenReturn(new IdDto(5L));

        ResponseEntity<IdDto> response = adminTransferController.importFromWeb(new WebImportDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(5L, response.getBody().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenExportTests() {
        when(transferService.exportTests(any())).thenReturn(new byte[]{2, 1, 0});

        ResponseEntity<byte[]> response = adminTransferController.exportTests(new ExportDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().length);
        assertEquals(2, response.getBody()[0]);
        assertEquals(1, response.getBody()[1]);
        assertEquals(0, response.getBody()[2]);
    }

    @Test
    void shouldReturnCorrectResponseWhenSearch() {
        when(transferService.search(any(), any())).thenReturn(
                new Page<>(
                        List.of(new ImportStatsDto(), new ImportStatsDto()),
                        2,
                        2));

        ResponseEntity<Page<ImportStatsDto>> response = adminTransferController.search(new AdminImportTestFilterDto(), new Pageable(2, 0));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getItems().size());
        assertEquals(2, response.getBody().getRows());
        assertEquals(2, response.getBody().getPageSize());
        assertEquals(1, response.getBody().getPagesCount());
    }
}
