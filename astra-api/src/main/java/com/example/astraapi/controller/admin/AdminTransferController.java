package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.dto.filter.AdminImportTestFilterDto;
import com.example.astraapi.dto.importing.FileImportDto;
import com.example.astraapi.dto.importing.ImportStatsDto;
import com.example.astraapi.dto.importing.WebImportDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.ADMIN_TRANSFER)
@RequiredArgsConstructor
public class AdminTransferController {
    private final TransferService transferService;

    @PostMapping(value = "/import/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<IdDto> importFromFile(@Valid @ModelAttribute FileImportDto fileImportDto) {
        IdDto idDto = transferService.importFromFile(fileImportDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(idDto);
    }

    @PostMapping("/import/web")
    public ResponseEntity<IdDto> importFromWeb(@Valid @RequestBody WebImportDto webImportDto) {
        IdDto idDto = transferService.importFromWeb(webImportDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(idDto);
    }

    @PostMapping("/export")
    public ResponseEntity<byte[]> exportTests(@Valid @RequestBody ExportDto exportDto) {
        byte[] bytes = transferService.exportTests(exportDto);
        return ResponseEntity.ok(bytes);
    }

    @PostMapping(value = "/import/stats/filter")
    public ResponseEntity<Page<ImportStatsDto>> search(
            @RequestBody AdminImportTestFilterDto filter,
            Pageable pageable
    ) {
        Page<ImportStatsDto> page = transferService.search(filter, pageable);
        return ResponseEntity.ok(page);
    }
}
